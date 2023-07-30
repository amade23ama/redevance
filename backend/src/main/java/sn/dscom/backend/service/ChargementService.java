package sn.dscom.backend.service;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.repository.ChargementRepository;
import sn.dscom.backend.service.converter.ChargementConverter;
import sn.dscom.backend.service.interfaces.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Chargement Service
 */
@Transactional
public class ChargementService implements IChargementService {

    /**
     * chargement Transformer
     */
    private final Transformer<ChargementDTO, ChargementEntity> chargementConverter = new ChargementConverter();


    /**
     * chargementRepository
     */
    private ChargementRepository chargementRepository;

    /**
     * site Service
     */
    private ISiteService siteService;

    /**
     * exploitation Service
     */
    private IExploitationService exploitationService;

    /**
     * produit Service
     */
    private IProduitService produitService;

    /**
     * voiture Service
     */
    private IVoitureService voitureService;

    /**
     * transporteur Service
     */
    private ITransporteurService transporteurService;

    /**
     * categorie Service
     */
    private ICategorieService categorieService;

    /**
     * construction
     * @param chargementRepository chargementRepository
     */
    @Builder
    public ChargementService(ChargementRepository chargementRepository, ISiteService siteService, IExploitationService exploitationService,
                             IProduitService produitService, IVoitureService voitureService, ITransporteurService transporteurService,
                             ICategorieService categorieService) {
        this.chargementRepository = chargementRepository;
        this.siteService = siteService;
        this.exploitationService = exploitationService;
        this.produitService = produitService;
        this.voitureService = voitureService;
        this.transporteurService = transporteurService;
        this.categorieService = categorieService;
    }

    /**
     * rechercher Chargements
     *
     * @return la liste des Chargements
     */
    @Override
    public Optional<List<ChargementDTO>> rechercherChargements() {
        // On charge l'ensemble des Chargements
        List<ChargementEntity> listChargementFind = this.chargementRepository.findAll();

        //retourne la liste
        return Optional.of(listChargementFind.stream()
                .map(chargementEntity -> this.chargementConverter.reverse(chargementEntity))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    /**
     * enregistrer Chargement
     *
     * @param chargementDTO le Chargement à enregistrer
     * @return le Chargement
     */
    @Override
    public Optional<ChargementDTO> enregistrerChargement(ChargementDTO chargementDTO) {
        //C'est la séquence qui génère l'id en cas de création
        return Optional.of(
                this.chargementConverter.reverse(this.chargementRepository.save(this.chargementConverter.transform(chargementDTO)))
        );
    }

    /**
     * compteur de Chargements
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre
     */
    public Integer compterChargement(final LocalDateTime dateMiseEnService) {
        return chargementRepository.compterChargementPardate(dateMiseEnService);

    }

    /**
     * effectuer Chargement
     * @param ligneChargement ligneChargement
     * @param mapCorrespondance mapCorrespondance
     * @param header header
     * @return ChargementDTO
     */
    @Override
    public ChargementDTO effectuerChargement(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header, DepotDTO depot){

        return ChargementDTO.builder()
                .dateCreation(new Date())
                .datePesage(new Date())
                .poids(20.3)
                .ecart(21.03)
                .poidsMax(40.00)
                .poidsSubst(30.0)
                .destination(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_chargement_destination"))).toUpperCase())
                .volumeMoyen(60.3)
                .volumeSubst(50.2)
                .vehicule(this.enregistrerVehicule(ligneChargement, mapCorrespondance, header))
                .site(this.rechercherSite(ligneChargement, mapCorrespondance, header))
                .exploitation(this.rechercherExploitation(ligneChargement, mapCorrespondance, header))
                .produit(this.rechercherProduit(ligneChargement, mapCorrespondance, header))
                .build();
    }

    /**
     * rechercher site en base
     *
     * @param ligneChargement siteDTO
     * @param mapCorrespondance maps
     * @return l'objet enregisté
     */
    private SiteDTO rechercherSite(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header){

        // Table SITE: nom et localité db_site_nom et db_site_localite
        //REFERIENTIEL: nom -> site sur fichier et alimenter la localité

        return this.siteService.rechercherSite(SiteDTO.builder()
                        .nom(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_site_nom"))).toUpperCase())
                        .build())
                .get()
                .stream()
                .findFirst().get();
    }


    /**
     * save en base
     *
     * @param ligneChargement ligneChargement
     * @param mapCorrespondance mapCorrespondance
     * @param header header
     * @return l'objet enregisté
     */
    private ExploitationDTO rechercherExploitation(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header){
        //site: site_origine et region
        //exploitation: nom -> Prevenance
        //REF recherche nom et alimenter la région
        return this.exploitationService.rechercherSiteExploitation(ExploitationDTO.builder()
                        .nom(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_exploitation_nom"))).toUpperCase())
                        .build())
                .get()
                .stream().findFirst().get();
    }

    /**
     * rechercher Produit
     *
     * @param ligneChargement ligneChargement
     * @param mapCorrespondance mapCorrespondance
     * @param header header
     * @return ProduitDTO
     */
    private ProduitDTO rechercherProduit(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header){

        return this.produitService.rechercherProduit(ProduitDTO.builder()
                        .nomSRC(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_produit_nom"))).toUpperCase())
                        .build())
                .get();

    }

    /**
     * save en base
     * @return l'objet enregisté
     */
    private VehiculeDTO enregistrerVehicule(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header){
        //VEHICULE: transpoteur et categorie
        //immatriculation -> immatriculation dans le fichier
        return this.voitureService.enregistrerVehicule(VehiculeDTO.builder()
                .dateCreation(new Date())
                        .transporteur(this.enregistrerTransporteur(ligneChargement, mapCorrespondance, header))
                        .categorie(this.enregistrerCategorie(ligneChargement, mapCorrespondance, header))
                .immatriculation(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_voiture_immatriculation"))).toUpperCase())
                .build()).get();
    }

    /**
     * save en base
     *
     * @param ligneChargement ligneChargement
     * @param mapCorrespondance mapCorrespondance
     * @param header header
     * @return l'objet enregisté
     */
    private CategorieDTO enregistrerCategorie(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header){
        //CATEGORIE:
        // type -> class dans le fichier et volume à voir
        return this.categorieService.enregistrerCategorie(CategorieDTO.builder()
                .dateCreation(new Date())
                .type(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_categorie_type"))).toUpperCase())
                .volume(20.2) //TODO: calcul du volume
                .build()).get();
    }

    /**
     * save en base
     *
     * @param ligneChargement ligneChargement
     * @param mapCorrespondance mapCorrespondance
     * @param header header
     *
     * @return l'objet enregisté
     */
    private TransporteurDTO enregistrerTransporteur(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header){
        //TRANSPORTEUR: NOM_RAISON_SOCIALE,TELEPHONE
        // type (PP ou PM), nom, prenom, raison social, telephone -> proprie/tel; mail? adress
        return this.transporteurService.enregistrerTransporteur(TransporteurDTO.builder()
                .dateCreation(new Date())
                .type("Personne physique")
                .nom(StringUtils.substringBefore(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_transporteur_nom"))),"/").toUpperCase())
                //.prenom(data[maps.get("transporteur").get("db_transporteur_nom")].toUpperCase())
                .telephone(StringUtils.substringAfter(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_transporteur_nom"))),"/"))
                //.email(data[maps.get("transporteur").get("db_transporteur_nom")].toUpperCase())
                //.adresse(data[maps.get("transporteur").get("db_transporteur_nom")].toUpperCase())
                .build()).get();
    }
}
