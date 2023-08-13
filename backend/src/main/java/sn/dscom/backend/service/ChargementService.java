package sn.dscom.backend.service;

import com.google.common.collect.Iterables;
import io.vavr.control.Try;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * TYPE_PERSONNE_PHYSIQUE
     */
    public static final String TYPE_PERSONNE_PHYSIQUE = "P";

    /** Logger Factory */
    static Logger log = LoggerFactory.getLogger(DepotController.class);

    /**
     * chargement Transformer
     */
    private final Transformer<ChargementDTO, ChargementEntity> chargementConverter = new ChargementConverter();


    /**
     * chargementRepository
     */
    private final ChargementRepository chargementRepository;

    /**
     * site Service
     */
    private final ISiteService siteService;

    /**
     * exploitation Service
     */
    private final IExploitationService exploitationService;

    /**
     * produit Service
     */
    private final IProduitService produitService;

    /**
     * voiture Service
     */
    private final IVoitureService voitureService;

    /**
     * transporteur Service
     */
    private final ITransporteurService transporteurService;

    /**
     * categorie Service
     */
    private final ICategorieService categorieService;

    /**
     * environment
     */
    private final Environment environment;

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
                .map(this.chargementConverter::reverse)
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
        String destination = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.chargement.destination"))));
        String poidsMesure = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.chargement.poids"))));
        String poidsMax = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.chargement.poidsMax"))));

        Double poidsEstime = ChargementUtils.getPoidsEstime(Double.valueOf(poidsMesure),Double.valueOf(poidsMax));

        Double volumeEstime = ChargementUtils.getVolumeEstime(poidsEstime, produitDTO.getDensiteKGM());

        Double ecart = ChargementUtils.getEcart(volumeEstime, vehiculeDTO.getCategorie().getVolume());

        // enregistrer Chargement
        return ChargementDTO.builder()
                            .dateCreation(new Date())
                            .datePesage(new Date())
                            .poids(Double.valueOf(poidsMesure))
                            //la diference entre le volume estimé et le volume du véhicule
                            .ecart(ecart)
                            .poidsMax(Double.valueOf(poidsMax))
                            // la difference entre le poids mesuré et le poids du véhicule à vide (25% du poids max)
                            .poidsSubst(poidsEstime)
                            .destination(destination)
                            //(Volume estimé - Volume classe)/2 = Ecart/2
                            .volumeMoyen(ecart/2)
                            //Poids Estimé / la densité du produit
                            .volumeSubst(volumeEstime)
                            .vehicule(vehiculeDTO)
                            .site(siteDTO)
                            .exploitation(exploitationDTO)
                            .produit(produitDTO)
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
        String siteName = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.site.nom"))));

        return Iterables.getOnlyElement(Try.of(() -> SiteDTO.builder()
                                                            .nom(siteName.trim().toUpperCase())
                                                            .build())
                                            .mapTry(this.siteService::rechercherSite)
                                            .onFailure(e -> ChargementService.log.error(String.format("Erreur lors de la recherche du Site : %s",e.getMessage())))
                                            .get().get());
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
        String exploitationName = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.exploitation.nom"))));

        return Iterables.getOnlyElement(Try.of(() -> ExploitationDTO.builder()
                                .nom(exploitationName.trim().toUpperCase())
                                .build())
                .mapTry(this.exploitationService::rechercherSiteExploitation)
                .onFailure(e -> ChargementService.log.error(String.format("Erreur lors de la recherche du Site d'exploitation: %s",e.getMessage())))
                .get()
                .get());
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

        // le nom du produit à rechercher
        String produitName = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.produit.nom"))));

        return Try.of(() -> ProduitDTO.builder()
                                .nomSRC(produitName.trim().toUpperCase())
                                .build())
                    .mapTry(this.produitService::rechercherProduit)
                    .onFailure(e -> ChargementService.log.error(String.format("Erreur lors de la recherche du Produit: %s",e.getMessage())))
                    .get().get();

    }

    /**
     * save en base
     * @return l'objet enregisté
     */
    private VehiculeDTO enregistrerVehicule(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header) throws DscomTechnicalException {
        //VEHICULE: transpoteur et categorie
        //immatriculation -> immatriculation dans le fichier
        String immatriculation = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.voiture.immatriculation"))));

        // rechercher la catégorie dans le référentiel
        CategorieDTO categorieDTO = this.rechercherCategorie(ligneChargement, mapCorrespondance, header);
        return Try.of(() -> VehiculeDTO.builder()
                                .dateCreation(new Date())
                                .transporteur(this.enregistrerTransporteur(ligneChargement, mapCorrespondance, header))
                                .categorie(categorieDTO)
                                .immatriculation(immatriculation.toUpperCase())
                                .build())
                .mapTry(this.voitureService::enregistrerVehicule)
                .onFailure(e -> ChargementService.log.error(String.format(" Erreur lors de la rechercher de la Vehicule: %s", e.getMessage())))
                .get().get();
    }

    /**
     * save en base
     *
     * @param ligneChargement ligneChargement
     * @param mapCorrespondance mapCorrespondance
     * @param header header
     * @return l'objet enregisté
     */
    private CategorieDTO rechercherCategorie(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header) throws DscomTechnicalException {
        //CATEGORIE:
        // type -> class dans le fichier et volume à voir
        String classeVehicule = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.categorie.type"))));

        return Iterables.getOnlyElement(Try.of(() -> CategorieDTO.builder()
                        .type(classeVehicule.trim().toUpperCase())
                        .build())
                .mapTry(this.categorieService::rechercherCategorie)
                .onFailure(e -> ChargementService.log.error(String.format(" Erreur lors de la rechercher de la categorie: %s", e.getMessage())))
                .get().get());
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
        return Try.of(() -> TransporteurDTO.builder()
                                    .dateCreation(new Date())
                                    .type(TYPE_PERSONNE_PHYSIQUE)
                                    .nom(ChargementUtils.getNomOrRaisonSociale(proprietaire))
                                    .telephone(ChargementUtils.getTelephone(proprietaire))
                                    .build())
                .mapTry(this.transporteurService::enregistrerTransporteur)
                .onFailure(e -> ChargementService.log.error(String.format(" Erreur lors de l'enregistrer du Transporteur: %s", e.getMessage())))
                .get().get();
    }
}
