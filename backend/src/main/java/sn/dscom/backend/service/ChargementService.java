package sn.dscom.backend.service;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import io.vavr.control.Try;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.ChargementUtils;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.*;
import sn.dscom.backend.database.repository.ChargementRepository;
import sn.dscom.backend.service.converter.*;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;
import sn.dscom.backend.service.interfaces.*;
import sn.dscom.backend.service.util.ChargementSpecifications;

import java.io.UnsupportedEncodingException;
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
    private static final Logger log = LoggerFactory.getLogger(ChargementService.class);

    /** POINT_VIRGULE_SEPARATEUR */
    public static final String POINT_VIRGULE_SEPARATEUR = ";";

    /**
     * chargement Transformer
     */
    private final Transformer<ChargementDTO, ChargementEntity> chargementConverter = new ChargementConverter();

    /**
     * explitation Converteur
     */
    private final Transformer<ExploitationDTO, ExploitationEntity> explitationConverteur = new ExploitationConverter();


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
     * depot Service
     */
    @Autowired
    private IDepotService depotService;

    /** Site Converteur */
    private final Transformer<SiteDTO, SiteEntity> siteConverteur = new SiteConverter();

    /** produit Converteur */
    private final Transformer<ProduitDTO, ProduitEntity> produitConverteur = new ProduitConverter();

    /**
     * vehicule Converter
     */
    private final Transformer<VehiculeDTO, VehiculeEntity> vehiculeConverter = new VehiculeConverter();

    /**
     * construction
     * @param chargementRepository chargementRepository
     */
    @Builder
    public ChargementService(ChargementRepository chargementRepository, ISiteService siteService, IExploitationService exploitationService,
                             IProduitService produitService, IVoitureService voitureService, ITransporteurService transporteurService,
                             ICategorieService categorieService, Environment environment, IDepotService depotService) {
        this.chargementRepository = chargementRepository;
        this.siteService = siteService;
        this.exploitationService = exploitationService;
        this.produitService = produitService;
        this.voitureService = voitureService;
        this.transporteurService = transporteurService;
        this.categorieService = categorieService;
        this.environment = environment;
        this.depotService = depotService;
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
                .filter(Objects::nonNull)
                .filter(ChargementEntity -> ChargementEntity.getDepots().size() > 0)
                .map(this.chargementConverter::reverse)
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
        Optional<ChargementEntity> chargementEntity = this.chargementRepository.isChargementExist(this.siteConverteur.transform(chargementDTO.getSite()),
                                                                                                    this.produitConverteur.transform(chargementDTO.getProduit()),
                                                                                                    this.vehiculeConverter.transform(chargementDTO.getVehicule()),
                                                                                                    chargementDTO.getDestination(),
                                                                                                    chargementDTO.getPoids(),
                                                                                                    chargementDTO.getPoidsMax());

        if (chargementEntity.isPresent() && (chargementDTO.getId() == null)){
            return Optional.of(this.chargementConverter.reverse(chargementEntity.get()));
        }

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
     */
    @Override
    public void effectuerChargement(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header, DepotDTO depot) throws DscomTechnicalException {
        ChargementService.log.info(String.format("Chargement de ligne : %s", ligneChargement));
        // rechercher du site d'Exploitation
        ExploitationDTO exploitationDTO = this.rechercherExploitation(ligneChargement, mapCorrespondance, header);
        // rechercher la catégorie dans le référentiel
        CategorieDTO categorieDTO = this.rechercherCategorie(ligneChargement, mapCorrespondance, header);
        // rechercher Site
        SiteDTO siteDTO = this.rechercherSite(ligneChargement, mapCorrespondance, header);
        // si on trouve pas la class, exploitation, site pessage
        if (exploitationDTO != null && categorieDTO != null && siteDTO != null){
            //rechercherDepotById
            DepotDTO depotCreat = this.depotService.rechercherDepotById(depot.getId()).get();
            // Enregistrement du véhicule
            VehiculeDTO vehiculeDTO = this.enregistrerVehicule(ligneChargement, mapCorrespondance, header, categorieDTO);
            // rechercher Produit
            ProduitDTO produitDTO = this.rechercherProduit(ligneChargement, mapCorrespondance, header);
            // Enregistrement du véhicule
            String destination = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.chargement.destination"))));
            String poidsMesure = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.chargement.poids"))));
            String poidsMax = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.chargement.poidsMax"))));
            String datePesage = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.chargement.date"))));
            String heurePesage = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.chargement.heure"))));


            // build Chargement
            ChargementDTO chargementDTO = buildChargement(vehiculeDTO, siteDTO, exploitationDTO, produitDTO, destination, poidsMesure, poidsMax, datePesage, heurePesage);

            Optional<ChargementEntity> chargementEntity = this.chargementRepository.isChargementExist(this.siteConverteur.transform(chargementDTO.getSite()),
                    this.produitConverteur.transform(chargementDTO.getProduit()),
                    this.vehiculeConverter.transform(chargementDTO.getVehicule()),
                    chargementDTO.getDestination(), chargementDTO.getPoids(),
                    chargementDTO.getPoidsMax());

            List<ChargementDTO> lisCharge = depotCreat.getChargementDTOList();
            if (chargementEntity.isPresent()) {
                chargementEntity.get().setDateModification(new Date());
                Integer nb = depotCreat.getNbChargementReDeposes() + 1;
                depot.setNbChargementReDeposes(nb);
                if (lisCharge.stream().noneMatch( x-> Objects.equals(x.getId(), chargementEntity.get().getId()))){
                    lisCharge.add(chargementConverter.reverse(chargementEntity.get()));
                }
                //this.enregistrerChargement(chargementDTO);
            }else {
                Integer nb = depotCreat.getNbChargementDeposes() + 1;
                depot.setNbChargementDeposes(nb);
                Optional<ChargementDTO> chargementEffectue = this.enregistrerChargement(chargementDTO);
                lisCharge.add(chargementEffectue.get());
            }
            depot.setSite(siteDTO);
            depot.setChargementDTOList(lisCharge);
            this.depotService.enregistrerDepot(depot);

        }else {
            String siteName = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.site.nom"))));
            String classeVehicule = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.categorie.type"))));
            String exploitationName = ligneChargement.get(header.indexOf(mapCorrespondance.get(environment.getProperty("db.exploitation.nom"))));

            ChargementService.log.info(String.format("Le site d'explitation %s, le site de pessage %s ou la classe de la voiture %s n'existe pas dans le reférentiel.", exploitationName, siteName, classeVehicule));
        }

    }

    /**
     * rechercher Chargements par:
     * -Origine
     * -Destination
     * -Site
     * -Date, l’utilisateur doit pouvoir rechercher:
     * -par année (exemple 2022)
     * -par date exacte (01/01/2022)
     * Il est possible de combiner les filtres sur plusieurs champs pour affiner sa recherche.
     *
     * @param chargementDTO chargementDTO
     * @return liste
     */
    @Override
    public Optional<List<ChargementDTO>> rechercherChargements(ChargementDTO chargementDTO) {
        // Rechercher les chargements en fonction de l'Origine
        Optional<List<ChargementEntity>> listChargement = Optional.of(new ArrayList<>());
        if (chargementDTO.getDatePesage() != null && !Strings.isNullOrEmpty(chargementDTO.getDestination())) {
            listChargement = this.chargementRepository.findChargementEntitiesByDatePassageAfterAndDestination(chargementDTO.getDatePesage(), chargementDTO.getDestination().trim().toUpperCase());

        }else if (!Strings.isNullOrEmpty(chargementDTO.getDestination())) {
            listChargement = this.chargementRepository.findChargementEntitiesByDestination(chargementDTO.getDestination().trim().toUpperCase());

        }else if (chargementDTO.getExploitation() != null && !Strings.isNullOrEmpty(chargementDTO.getExploitation().getNom())) {
            listChargement = this.chargementRepository.findChargementEntitiesByExploitationEntity(this.explitationConverteur.transform(chargementDTO.getExploitation()));

        }else if (chargementDTO.getSite() != null && !Strings.isNullOrEmpty(chargementDTO.getSite().getNom())) {
            listChargement = this.chargementRepository.findChargementEntitiesBySiteEntity(this.siteConverteur.transform(chargementDTO.getSite()));

        }else if (chargementDTO.getDatePesage() != null) {
            listChargement = this.chargementRepository.findChargementEntitiesByDatePassageAfter(chargementDTO.getDatePesage());

        }

        return Optional.of(
                Try.of(listChargement::get).get()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(ChargementEntity -> ChargementEntity.getDepots().size() > 0)
                        .map(this.chargementConverter::reverse)
                        .collect(Collectors.toList()));
    }

    /**
     * quantite Par Region Par An
     * @param listExploitation  listExploitation
     * @param dateDebut  dateDebut
     * @param dateFin  dateFin
     * @return la quantité
     */
    @Override
    public double getQuantiteParRegionParAn(List<ExploitationDTO> listExploitation, Date dateDebut, Date dateFin) {
        List<ExploitationEntity> listExploitationEntity = listExploitation.stream()
                .map(this.explitationConverteur::transform)
                .toList();
        Double value = this.chargementRepository.quantiteParRegionParAn(listExploitationEntity, dateDebut, dateFin);

        if (value == null)
            return 0;

        return value;
    }

    /**
     * get Quantite Produit ParAn
     * @param produit produit
     * @param dateDebut dateDebut
     * @param dateFin dateFin
     * @return la liste
     */
    @Override
    public double getQuantiteProduitParAn(ProduitDTO produit, Date dateDebut, Date dateFin) {
        ProduitEntity produitEntity = this.produitConverteur.transform(produit);
        Double value = this.chargementRepository.quantiteByProductByYear(produitEntity, dateDebut, dateFin);

        if (value == null)
            return 0;

        return value;
    }

    /**
     * get Recouvrement Produit Par Année
     * @param listProduit listProduit
     * @param dateDebut dateDebut
     * @param dateFin dateFin
     * @return la liste
     */
    @Override
    public double getRecouvrementProduitParAn(List<ProduitDTO> listProduit, Date dateDebut, Date dateFin) {
        List<ProduitEntity> listProduitEntity = listProduit.stream()
                .map(this.produitConverteur::transform)
                .toList();
        Double value = this.chargementRepository.quantiteRecouvrementAnnuel(listProduitEntity, dateDebut, dateFin);

        if (value == null)
            return 0;

        return value;
    }

    /**
     * getListeAnnee
     *
     * @return liste
     */
    @Override
    public List<Integer> getListeAnnee() {
        return this.chargementRepository.getListeAnnee();
    }

    /**
     * @param dateDebutAnnee dateDebutAnnee
     * @param dateFinAnnee   dateDebutAnnee
     * @return liste
     */
    @Override
    public Integer getChargementsAnnuel(Date dateDebutAnnee, Date dateFinAnnee) {
        return this.chargementRepository.countChargementAnnuel( dateDebutAnnee, dateFinAnnee);
    }

    /**
     * buildChargement
     * @param vehiculeDTO vehiculeDTO
     * @param siteDTO siteDTO
     * @param exploitationDTO exploitationDTO
     * @param produitDTO produitDTO
     * @param destination destination
     * @param poidsMesure poidsMesure
     * @param poidsMax poidsMax
     * @return ChargementDTO
     */
    private static ChargementDTO buildChargement(VehiculeDTO vehiculeDTO, SiteDTO siteDTO, ExploitationDTO exploitationDTO, ProduitDTO produitDTO, String destination, String poidsMesure, String poidsMax, String date, String heure) {

        Double poidsEstime = ChargementUtils.getPoidsEstime(Double.valueOf(poidsMesure),Double.valueOf(poidsMax), vehiculeDTO.getPoidsVide());

        Double volumeEstime = ChargementUtils.getVolumeEstime(poidsEstime, produitDTO.getDensiteKGM());

        Double ecart = ChargementUtils.getEcart(volumeEstime, vehiculeDTO.getCategorie().getVolume());

        return ChargementDTO.builder()
                .dateCreation(new Date())
                .datePesage(ChargementUtils.getDateHeureChargement(date, heure))
                .poids(Double.valueOf(poidsMesure))
                //la diference entre le volume estimé et le volume du véhicule
                .ecart(ecart)
                .poidsMax(Double.valueOf(poidsMax))
                // la difference entre le poids mesuré et le poids du véhicule à vide (25% du poids max)
                .poidsSubst(poidsEstime)
                .destination(destination)
                //(Volume estimé + Volume classe)/2 = Ecart/2
                .volumeMoyen(ChargementUtils.getVolumeMoyen(volumeEstime, vehiculeDTO.getCategorie().getVolume()))
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
    private VehiculeDTO enregistrerVehicule(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header, CategorieDTO categorieDTO) throws DscomTechnicalException {
        //VEHICULE: transpoteur et categorie
        //immatriculation -> immatriculation dans le fichier
        String immatriculation = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.voiture.immatriculation"))));

        return Try.of(() -> VehiculeDTO.builder()
                                .dateCreation(new Date())
                                .transporteur(this.enregistrerTransporteur(ligneChargement, mapCorrespondance, header))
                                .categorie(categorieDTO)
                                .immatriculation(ChargementUtils.replaceAllSpecialCarater(immatriculation.toUpperCase()))
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


    @Override
    public List<ChargementDTO> rechargementParCritere(CritereRecherche<?> critereRecherche) {

        List<Long> idsSite = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == SiteEntity.class)
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());
        List<Long> idsProduit = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == ProduitEntity.class)
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());
        List<Long> idsSiteExploitation = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == ExploitationEntity.class)
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());
        List<Long> idsVehicules = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == VehiculeEntity.class)
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());
        List<String> regions = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == String.class)
                .filter(item -> ((AutocompleteRecherche) item).getOrigine().equals("Region"))
                .map(item ->((AutocompleteRecherche) item).getId())
                .toList());

        List<String> localites = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == String.class)
                .filter(item -> ((AutocompleteRecherche) item).getOrigine().equals("Localite"))
                .map(item ->((AutocompleteRecherche) item).getId())
                .toList());

        Specification<ChargementEntity> spec = Specification
                .where(ChargementSpecifications.withSiteIdsAndProduitIds(idsSite,idsProduit,idsSiteExploitation,idsVehicules,regions,
                        localites,critereRecherche.getAnnee()));

        List<ChargementEntity> listSitesFind= chargementRepository.findAll(spec);
        return listSitesFind.stream()
                .filter(Objects::nonNull)
                .filter(ChargementEntity -> ChargementEntity.getDepots().size() > 0)
                .map(this.chargementConverter::reverse)
                .collect(Collectors.toList());
    }

    /**
     * chargementDTOs To Bytes
     * <p>
     * chargementDTOsToBytes
     *
     * @param datas le liste
     * @return le fichier en byte
     */
    @Override
    public byte[] chargementDTOsToBytes(List<ChargementDTO> datas) throws UnsupportedEncodingException {

        // Le builder
        StringBuilder csvBuilder= new StringBuilder();
        // L'en-tête
        csvBuilder.append("DATE DE PESAGE;ORIGINE;REGION;DESTINATION;PRODUIT;TRANSPORTEUR;TELEPHONE;VEHICULE;CLASSE;POIDS;POIDS ESTIME;VOLUME;VOLUME MOYEN;ECART\r\n");

        //on parcours la liste des chargements pour contruire un ligne du fichier
        datas.forEach(data -> {
            csvBuilder.append(data.getDateCreation().toString()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getExploitation().getNom()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getExploitation().getRegion()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getDestination()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getProduit().getNomSRC()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getVehicule().getTransporteur().getNom()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getVehicule().getTransporteur().getTelephone()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getVehicule().getImmatriculation()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getVehicule().getCategorie().getType()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getPoids()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getPoidsSubst()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getVolumeSubst()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getVolumeMoyen()).append(POINT_VIRGULE_SEPARATEUR)
                    .append(data.getEcart())
                    .append("\r\n");
        });

        return csvBuilder.toString().getBytes("windows-1252");
    }

    /**
     * chargerChargementParId
     *
     * @param id id
     * @return ChargementDTO
     */
    @Override
    public ChargementDTO chargerChargementParId(Long id) {

        ChargementService.log.info(String.format("Recherche du chargement : %s", id));
        Optional<ChargementEntity> chargement = chargementRepository.findById(id);
        // on retourne le produit trouvé
        if (chargement.isPresent()) {
            return Try.of(chargement::get)
                    .filter(Objects::nonNull)
                    .filter(ChargementEntity -> ChargementEntity.getDepots().size() > 0)
                    .mapTry(this.chargementConverter::reverse)
                    .onFailure(e -> ChargementService.log.error(String.format("Erreur leur du reverse du chargement %s ",e.getMessage())))
                    .get();
        } else {
            ChargementService.log.info(String.format("Le chargement d'id %s n'est pas trouvé en base ", id));
            throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
        }
    }

    /**
     * modifierChargement
     *
     * @param chargementDTO chargementDTO
     * @return ChargementDTO
     */
    @Override
    public ChargementDTO modifierChargement(ChargementDTO chargementDTO) {
        ChargementService.log.info(String.format("modification du chargement : %s", chargementDTO.getId()));
        Optional<ChargementEntity> chargementFind = this.chargementRepository.findById(chargementDTO.getId());

        // si chargement trouvé, on la modifie
        if(chargementFind.isPresent()){
            ChargementEntity chargementtoSave = chargementFind.get();
            chargementtoSave.setDateModification(new Date());
            chargementtoSave.setDestination(chargementDTO.getDestination().trim());

            // On recupère le produit dans le référentiel
            ProduitEntity produitToSave = Try.of(() -> this.produitService.rechercherProduit(chargementDTO.getProduit()).get())
                    .mapTry(this.produitConverteur::transform)
                    .get();

            //Recalcule des données de chargement
            Double volumeEstime = ChargementUtils.getVolumeEstime(chargementtoSave.getPoidsSubsitance(), produitToSave.getDensiteKGM());
            Double volumeVehicule = chargementtoSave.getVehiculeEntity().getCategorieEntity().getVolume();
            Double ecart = ChargementUtils.getEcart(volumeEstime, volumeVehicule);

            chargementtoSave.setProduitEntity(produitToSave);
            chargementtoSave.setVolumeSubsitance(volumeEstime);
            chargementtoSave.setVolumeMoyen(ChargementUtils.getVolumeMoyen(volumeEstime, volumeVehicule));
            chargementtoSave.setEcart(ecart);

            return Try.of(() -> this.chargementRepository.save(chargementtoSave))
                    .mapTry(this.chargementConverter::reverse)
                    .get();
        }
        ChargementService.log.info(String.format("Le chargement d'id %s n'est pas trouvé en base ", chargementDTO.getId()));
        throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
    }

    /**
     * supprimerChargement Par Id
     *
     * @param listChargementDTO chargementDTO
     * @return true or false
     */
    @Override
    public Boolean supprimerChargementParId(List<ChargementDTO> listChargementDTO) {
        return listChargementDTO.stream().allMatch(this::supprimerChargement);
    }

    /**
     * supprimerChargementBycritere
     *
     * @param critereRecherche critereRecherche
     * @return true or false
     */
    @Override
    public Boolean supprimerChargementBycritere(CritereRecherche critereRecherche) {
        List<ChargementDTO> listChrgmentToDelete = this.rechargementParCritere(critereRecherche);
        return listChrgmentToDelete.stream().allMatch(this::supprimerChargement);
    }

    /**
     * supprimerChargement
     * @param chargementDTO chargementDTO
     * @return true or false
     */
    private boolean supprimerChargement(ChargementDTO chargementDTO) {
        // Find the chargement
        Optional<ChargementEntity> chargementEntityFind = this.chargementRepository.findById(chargementDTO.getId());
        if (chargementEntityFind.isPresent()) {
            ChargementEntity chargementEntity = chargementEntityFind.get();
            if (chargementEntity.getDepots().size() == 1 || chargementEntity.getDepots().size() == 0) {
                chargementEntity.setDepots(null);
                ChargementEntity chargementToDelete = this.chargementRepository.save(chargementEntity);
                this.chargementRepository.deleteById(chargementToDelete.getId());
            } else {
                var list = chargementEntity.getDepots().subList(1, chargementEntity.getDepots().size());
                chargementEntity.setDepots(list);
                this.chargementRepository.save(chargementEntity);
                //this.chargementRepository.deleteById(chargementEntity.getId());
           }

           return true;
        }
        return  false;
    }

}
