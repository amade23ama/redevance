package sn.dscom.backend.service;

import io.vavr.control.Try;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.util.ChargementUtils;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.controller.DepotController;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.repository.ChargementRepository;
import sn.dscom.backend.service.converter.ChargementConverter;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;
import sn.dscom.backend.service.interfaces.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Chargement Service
 */
@Transactional
public class ChargementService implements IChargementService {

    /** Logger Factory */
    static Logger log = LoggerFactory.getLogger(DepotController.class);

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
     * environment
     */
    private Environment environment;

    /**
     * construction
     * @param chargementRepository chargementRepository
     */
    @Builder
    public ChargementService(ChargementRepository chargementRepository, ISiteService siteService, IExploitationService exploitationService,
                             IProduitService produitService, IVoitureService voitureService, ITransporteurService transporteurService,
                             ICategorieService categorieService, Environment environment) {
        this.chargementRepository = chargementRepository;
        this.siteService = siteService;
        this.exploitationService = exploitationService;
        this.produitService = produitService;
        this.voitureService = voitureService;
        this.transporteurService = transporteurService;
        this.categorieService = categorieService;
        this.environment = environment;
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
    public ChargementDTO effectuerChargement(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header, DepotDTO depot) throws DscomTechnicalException {

        // Enregistrement du véhicule
        VehiculeDTO vehiculeDTO = this.enregistrerVehicule(ligneChargement, mapCorrespondance, header);

        // rechercher Site
        SiteDTO siteDTO = this.rechercherSite(ligneChargement, mapCorrespondance, header);

        // rechercher du site d'Exploitation
        ExploitationDTO exploitationDTO = this.rechercherExploitation(ligneChargement, mapCorrespondance, header);

        // rechercher Produit
        ProduitDTO produitDTO = this.rechercherProduit(ligneChargement, mapCorrespondance, header);

        // Enregistrement du véhicule
        String destination = ligneChargement.get(header.indexOf(mapCorrespondance.get("db_chargement_destination"))).toUpperCase();

        // enregistrer Chargement
        ChargementDTO chargementDTO = ChargementDTO.builder()
                                                    .dateCreation(new Date())
                                                    .datePesage(new Date())
                                                    .poids(25.3)
                                                    .ecart(23.03)
                                                    .poidsMax(42.00)
                                                    .poidsSubst(31.1)
                                                    .destination(destination)
                                                    .volumeMoyen(61.3)
                                                    .volumeSubst(50.2)
                                                    .vehicule(vehiculeDTO)
                                                    .site(siteDTO)
                                                    .exploitation(exploitationDTO)
                                                    .produit(produitDTO)
                                                    .build();
        return chargementDTO;
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
        return Try.of(() -> ExploitationDTO.builder()
                                .nom(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_exploitation_nom"))).toUpperCase())
                                .build())
                .mapTry(this.exploitationService::rechercherSiteExploitation)
                .onFailure(e -> ChargementService.log.error(String.format("Erreur lors de la recherche du Site d'exploitation: %s",e.getMessage())))
                .get()
                .get()
                .stream()
                .filter(Objects::nonNull)
                .findFirst().get();
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

        return Try.of(() -> ProduitDTO.builder()
                                .nomSRC(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_produit_nom"))).toUpperCase())
                                .build())
                    .mapTry(this.produitService::rechercherProduit)
                    .onFailure(e -> ChargementService.log.error("Erreur lors de la recherche du Produit: %s",e.getMessage()))
                    .get().get();

    }

    /**
     * save en base
     * @return l'objet enregisté
     */
    private VehiculeDTO enregistrerVehicule(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header) throws DscomTechnicalException {
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
    private CategorieDTO enregistrerCategorie(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header) throws DscomTechnicalException {
        //CATEGORIE:
        // type -> class dans le fichier et volume à voir
        return Try.of(() -> CategorieDTO.builder()
                                .dateCreation(new Date())
                                .type(ligneChargement.get(header.indexOf(mapCorrespondance.get("db_categorie_type"))).toUpperCase())
                                .volume(20.2) //TODO: calcul du volume
                                .build())
                .mapTry(this.categorieService::enregistrerCategorie)
                .onFailure(e -> ChargementService.log.error(": %s", e.getMessage()))
                .get().get();
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
        /** proprietaire : Nom-RaisonSociale/telephone */
        String proprietaire = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.transporteur.nom"))));
        return this.transporteurService.enregistrerTransporteur(TransporteurDTO.builder()
                .dateCreation(new Date())
                .type("Personne physique")
                .nom(ChargementUtils.getNomOrRaisonSociale(proprietaire))
                //.prenom(data[maps.get("transporteur").get("db_transporteur_nom")].toUpperCase())
                .telephone(ChargementUtils.getTelephone(proprietaire))
                //.email(data[maps.get("transporteur").get("db_transporteur_nom")].toUpperCase())
                //.adresse(data[maps.get("transporteur").get("db_transporteur_nom")].toUpperCase())
                .build()).get();
    }
}
