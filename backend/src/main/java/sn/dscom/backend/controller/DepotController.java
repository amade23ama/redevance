package sn.dscom.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.constants.Enum.StatutEnum;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.database.entite.UtilisateurEntity;
import sn.dscom.backend.service.ChargementService;
import sn.dscom.backend.service.ConnectedUtilisateurService;
import sn.dscom.backend.service.ErreurDepotService;
import sn.dscom.backend.service.converter.UtilisateurConverter;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;
import sn.dscom.backend.service.interfaces.*;
import sn.dscom.backend.service.mail.EmailDetails;
import sn.dscom.backend.service.mail.IMailService;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité de depot
 * @version 1
 */
@RestController
@RequestMapping("/api/v1/depot")
public class DepotController {

    /** Logger Factory */
   private static final Logger log= LoggerFactory.getLogger(DepotController.class);

    /**
     * depot Service
     */
    @Autowired
    private IDepotService depotService;
    @Autowired
    private ErreurDepotService erreurDepotService;

    /**
     * environment
     */
    @Autowired
    private Environment environment;

    /**
     * connected Utilisateur Service
     */
    @Autowired
    private ConnectedUtilisateurService connectedUtilisateurService;

    /**
     * site Service
     */
  //  @Autowired
  //  private ISiteService siteService;

    /** mailService */
  //  @Autowired
    private IMailService mailService;

    /** exploitation Service */
    @Autowired
    private IExploitationService exploitationService;

    /** categorie Service */
    @Autowired
    private ICategorieService categorieService;

    /**
     * get header
     *
     * @return l'entete
     */
    @PostMapping(path = "/fileHeader")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<FileInfoDTO> getFileHeader(@RequestParam("file") MultipartFile file){
        // la liste des colonnes du fichier
        List<String> header = null;
        // Le fichier contient des colonnes à ne pas mapper
        List<String> colonnesToIgnore = Splitter.on(",").splitToList(environment.getProperty("list.file.colonne.to.ignore"));
        DepotController.log.info(" entete du fichier ");
        if (file.isEmpty()) {
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_FiLE_NOT_FOUND);
        }
        try{
            CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream())) ;
            header = tabToList(csvReader.readNext());
            DepotController.log.info(" entete du fichier "+header);
        }catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_INATTENDUE);
        }
        // la liste épurée des colonnes à mapper
       List<String> colonnesToMap = header.stream()
                .filter(Predicates.compose(colonnesToIgnore::contains, Functions.identity()).negate())
                .toList();
        return  ResponseEntity.ok(FileInfoDTO.builder()
                .enteteFile(colonnesToMap)
                .colonneTable(Arrays.asList(environment.getProperty("list.table.colonne").split(",")))
                .build());
    }

    /**
     * get header
     *
     * @param file transporteurDTO
     * @return l'entete
     */
    /*@PostMapping(path = "/upload")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<Long> uploadFile(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("mapEntete") String mapEnteteJson,
                                                   @RequestParam("nom") String nomDepot) throws IOException, ExecutionException, InterruptedException {
        // // On charge tous les référentiels en parallele
        UtilisateurDTO utilisateurDTO = this.getConnectedUtilisateur().get();
        List<ProduitDTO> referentielProduits = this.getReferentielProduit().get();
        List<ExploitationDTO> referentielSitesExploitation = this.getReferentielSitesExploitation().get();
        List<CategorieDTO> referentielCategorie = this.getReferentielCategories().get();
        List<SiteDTO> referentielSite = this.getReferentielSites().get();
        // Mapper
        ObjectMapper objectMapper = new ObjectMapper();
        // Le Depot
        DepotDTO depot = new DepotDTO();
        String nom;
        SiteDTO siteDTO = null;
        // Controle sur le fichier
        if (file.isEmpty()) {throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_FiLE_NOT_FOUND);}
        try {
            Map<String, String> mapDatabaseEnteteFile = objectMapper.readValue(mapEnteteJson,new TypeReference<Map<String, String>>() {});
            nom = objectMapper.readValue(nomDepot, new TypeReference<String>() {});
            Map<String, String> mapInverse = new HashMap<>();
            //La map est inversée
            mapDatabaseEnteteFile.forEach((k, v) -> {mapInverse.put(v,k);});
            // enregister le depot
            depot = this.depotService.enregistrerDepot(buildDepot(file, utilisateurDTO, nom, StatutEnum.ENCOURS.getCode())).get();
            List<String> header = null;
            try{
                CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
                header = tabToList(csvReader.readNext());
                // next Line
                String [] nextLine;
                // Chaque ligne du fichier est un chargement
                while ((nextLine = csvReader.readNext()) != null)
                {
                    // Ligne de chargement
                    List<String> chargement = tabToList(nextLine);
                    DepotController.log.info(String.format("Chargement de ligne : %s", chargement));
                    //Certains fichier csv peuvent contenir de ligne vide dans ce cas on ne traite pas la ligne
                    if (chargement.size() != 0){
                        // rechercher Site
                        if(null == siteDTO) {
                            siteDTO = this.rechercherSite(chargement, mapInverse, header, referentielSite);
                        }
                        // On recupère le produit dans le chagement: On fait un chargement que pour les produit qui existe
                        Optional<ProduitDTO> produitDTO = this.getCurrentProduct(chargement, mapInverse, header, referentielProduits).get();
                        // On recupère le siteExploitation dans le chagement: On fait un chargement que pour les sites qui existe
                        Optional<ExploitationDTO> exploitationDTO = this.getCurrentExploitation(chargement, mapInverse, header, referentielSitesExploitation).get();
                        // On recupère le siteExploitation dans le chagement: On fait un chargement que pour les sites qui existe
                        Optional<CategorieDTO> categorieDTO = this.getCurrentCategorie(chargement, mapInverse, header, referentielCategorie).get();
                        if(produitDTO.isPresent() && exploitationDTO.isPresent()  && categorieDTO.isPresent() && siteDTO != null){
                            //Chargement d'une ligne du fichier
                            this.chargementService.effectuerChargement(chargement, mapInverse, header, depot, produitDTO.get(), exploitationDTO.get(), categorieDTO.get(), siteDTO);
                        }
                    }
                }
                //rechercherDepotById
                DepotDTO depotCreat = this.depotService.rechercherDepotById(depot.getId()).get();
                depot.setDateHeureFinDepot( new Date());
                depot.setStatut(StatutEnum.SUCCES.getCode());
                depot.setNbChargementReDeposes(depotCreat.getNbChargementReDeposes());
                depot.setNbChargementDeposes(depotCreat.getNbChargementDeposes());
                depot.setNbChargementErreur(depotCreat.getNbChargementErreur());
                this.depotService.enregistrerDepot(depot);
                log.info(" entete du fichier "+header);
                //Envoie du mail
                this.envoiMail(utilisateurDTO);
            }catch (IOException | CsvValidationException e) {
                e.printStackTrace();
                // mise à jour du dépot
                depot.setStatut(StatutEnum.ERREUR.getCode());
                depot.setSite(siteDTO);
                this.definirDepot(depot);
                throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_INATTENDUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mise à jour du dépot
            depot.setStatut(StatutEnum.ERREUR.getCode());
            depot.setSite(siteDTO);
            this.definirDepot(depot);
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_FiLE_NOT_FOUND);
        }
        // return id du depot
        return   ResponseEntity.ok(depot.getId());
    }
    */

    /**
     * getCurrentProduct
     * @return CompletableFuture<Optional<ProduitDTO>>
     */
   /* @Async
    protected CompletableFuture<Optional<ExploitationDTO>> getCurrentExploitation(List<String> chargement, Map<String, String> mapInverse, List<String> header,List<ExploitationDTO> ref){
        String siteExploitation = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.exploitation.nom")))).toUpperCase();
        Optional<ExploitationDTO> exploitationDTO = ref.stream().filter(exploita -> siteExploitation.trim().equals(exploita.getNom())).findFirst();
        if (exploitationDTO.isEmpty()) {
            DepotController.log.info(String.format("Le site d'explitation %s n'existe pas dans le reférentiel.", siteExploitation));
        }
        return CompletableFuture.completedFuture(exploitationDTO);
    }
    */

    /**
     * getCurrentProduct
     * @return CompletableFuture<Optional<ProduitDTO>>
     */
   /* @Async
    protected CompletableFuture<Optional<ProduitDTO>> getCurrentProduct(List<String> chargement, Map<String, String> mapInverse, List<String> header,List<ProduitDTO> referentielProduits){
        String nomProduit = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.produit.nom")))).toUpperCase();
        Optional<ProduitDTO> produitDTO = referentielProduits.stream().filter(produit -> nomProduit.trim().equals(produit.getNomSRC())).findFirst();
        if (produitDTO.isEmpty()) {
            DepotController.log.info(String.format("Le produit %s n'existe pas dans le reférentiel.", nomProduit));
        }
        return CompletableFuture.completedFuture(produitDTO);
    }
    */

    /**
     * getCurrentCategorie
     * @return CompletableFuture<Optional<CategorieDTO>>
     */
   /* @Async
    protected CompletableFuture<Optional<CategorieDTO>> getCurrentCategorie(List<String> chargement, Map<String, String> mapInverse, List<String> header,List<CategorieDTO> ref){
        String categorie = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.categorie.type")))).toUpperCase();
        Optional<CategorieDTO> categorieDTO = ref.stream().filter(classe -> categorie.trim().equals(classe.getType())).findFirst();
        if (categorieDTO.isEmpty()) {
            DepotController.log.info(String.format("La catégorie %s n'existe pas dans le reférentiel.", categorie));
        }
        return CompletableFuture.completedFuture(categorieDTO);
    }
    */


    /**
     * getReferentielProduit
     * @return CompletableFuture<List<ProduitDTO>>
     */
    /*@Async
    protected CompletableFuture<List<ProduitDTO>> getReferentielProduit(){
        List<ProduitDTO> referentielProduits = this.produitService.rechercherProduits().get();
        return CompletableFuture.completedFuture(referentielProduits);
    }
    */

    /**
     * getReferentielSitesExploitation
     * @return CompletableFuture<List<ExploitationDTO>>
     */
   /* @Async
    protected CompletableFuture<List<ExploitationDTO>> getReferentielSitesExploitation(){
        List<ExploitationDTO> referentielSitesExploitation = this.exploitationService.rechercherSitesExploitation().get();
        return CompletableFuture.completedFuture(referentielSitesExploitation);
    }
    */

    /**
     * getReferentielCategories
     * @return CompletableFuture<List<ExploitationDTO>>
     */
    /*@Async
    protected CompletableFuture<List<CategorieDTO>> getReferentielCategories(){
        List<CategorieDTO> referentielCategorie = this.categorieService.rechercherCategories().get();
        return CompletableFuture.completedFuture(referentielCategorie);
    }
    */


    /**
     * getReferentielSites
     * @return CompletableFuture<List<SiteDTO>>
     */
    /*
    @Async
    protected CompletableFuture<List<SiteDTO>> getReferentielSites(){
        List<SiteDTO> referentielSite = this.siteService.rechercherSites().get();
        return CompletableFuture.completedFuture(referentielSite);
    }
*/
    /**
     * getConnectedUtilisateur
     * @return CompletableFuture<UtilisateurDTO>
     */
    /*@Async
    protected CompletableFuture<UtilisateurDTO> getConnectedUtilisateur(){
        return CompletableFuture.completedFuture(UtilisateurConverter.toUtilisateurDTO(connectedUtilisateurService.getConnectedUtilisateur()));
    }
    */


    /**
     * tabToList
     * @param nextLine nextLine
     * @return List
     */
    private static List<String> tabToList(String[] nextLine) {
        return Arrays.stream(nextLine)
                .flatMap(line -> Arrays.stream(line.split(";")))
                .collect(Collectors.toList());
    }

    /**
     * buildDepot
     *
     * @param file file
     * @param utilisateurDTO utilisateurDTO
     * @param nom nom
     * @return DepotDTO
     */
    private static DepotDTO buildDepot(MultipartFile file, UtilisateurDTO utilisateurDTO, String nom, String statut) {
        return DepotDTO.builder()
                .nom(nom)
                .statut(statut)
                .nbChargementReDeposes(0)
                .nbChargementDeposes(0)
                .nomFichier(file.getOriginalFilename())
                .nbChargementErreur(0)
                .dateHeureDepot(new Date())
                .deposeur(utilisateurDTO)
                .build();
    }

    /**
     * rechercher depots
     * @return la liste
     */
    @GetMapping(path = "/rechercher")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<DepotDTO>> rechercherDepot() {
        //rechercher all Depots
        Optional<List<DepotDTO>> list = depotService.rechercherDepots();

        // Appel du service rechercherDepot
        // si vide on retour une erreur 404
        return cyclops.control.Try.withCatch(list::get)
                .peek(listDepotDTO -> DepotController.log.info(String.format("DepotController -> rechercherDepot: %s", listDepotDTO.size())))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * rechercher depot by id
     * @param id TransporteurDTO
     * @return la liste avec une aucurence
     */
    @GetMapping(path = "/rechercherById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<DepotDTO> rechercherDepotById(@PathVariable long id) {
        //rechercher Depot By Id
        return  ResponseEntity.ok(depotService.rechercherDepotById(id).get());
    }

    /**
     * rechercher Depot By Criteres
     *
     * @param depotDTO depotDTO
     * @return la liste
     */
    @PostMapping(path = "/rechercherByCriteria")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<DepotDTO>> rechercherDepotByCriteres(@PathVariable DepotDTO depotDTO) {
        //rechercher Depot By Criteres
        Optional<List<DepotDTO>> list = depotService.rechercherDepotByCriteres(depotDTO);

        // Appel du service rechercherProduits
        // si vide on retour une erreur 404
        return cyclops.control.Try.withCatch(list::get)
                .peek(listDepotDTO -> DepotController.log.info(String.format("DepotController -> rechercherDepotByCriteres: %s", listDepotDTO.size())))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * a la fin du dépot ou lors que le depot est en erreur
     *
     * @param depot the depot
     */
    private void definirDepot( final DepotDTO depot){
        // on recupère le dépot et on la met à jour le compteur d'erreur et la date de fin du chargment
        DepotDTO depotDTO = this.depotService.rechercherDepotById(depot.getId()).get();
        int nbErreur = depotDTO.getNbChargementErreur();
        depotDTO.setNbChargementErreur(nbErreur + 1);
        depotDTO.setStatut(depot.getStatut());
        depotDTO.setDateHeureFinDepot(new Date());
        depotDTO.setSite(depot.getSite());
        Try.of(() -> depotDTO)
                .mapTry(this.depotService::enregistrerDepot);
    }

    /**
     * rechercher site en base
     *
     * @param ligneChargement siteDTO
     * @param mapCorrespondance maps
     * @return l'objet enregisté
     */
   /* private SiteDTO rechercherSite(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header, List<SiteDTO> referentielSite){
        String siteName = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.site.nom")))).toUpperCase();
        Optional<SiteDTO> siteDTO = referentielSite.stream().filter(site -> siteName.trim().equals(site.getNom())).findFirst();
        if (siteDTO.isEmpty()) {
            DepotController.log.info(String.format("Le site de pesage %s n'existe pas dans le reférentiel.", siteName));
        }
        return siteDTO.orElse(null);

    }
    */

    /**
     * rechargementParCritere
     * @param critereRecherche critereRecherche
     * @return la list
     */
    @PostMapping (value = "/rechercheBy")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<Page<DepotDTO>> rechargementParCritere(@RequestBody CritereRecherche<?> critereRecherche) {
        Page<DepotDTO> list=this.depotService.rechargementParCritere(critereRecherche);
        return ResponseEntity.ok(list);
    }

    /**
     * methode d'envoi de Mail
     */
    private void envoiMail(UtilisateurDTO utilisateurDTO) {

        this.mailService.envoiMail(EmailDetails.builder()
                .recipient(utilisateurDTO.getEmail())
                .subject("Fin de chargement")
                .msgBody("Votre chargement est terminé.")
                .build());
    }
    /**
     * rechercher depot by id
     * @param id TransporteurDTO
     * @return la liste avec une aucurence
     */
    @GetMapping(path = "/rechercherrechercherErreurById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public List<ErreurDepotDTO> rechercherErreurDepotById(@PathVariable long id) {
        ErreurDepotDTO erreurDepotDTO=ErreurDepotDTO.builder().idDepot(id).build();
        return  erreurDepotService.rechercherErreurDepotByCriteres(erreurDepotDTO).get();
    }
}
