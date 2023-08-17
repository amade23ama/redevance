package sn.dscom.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.constants.Enum.StatutEnum;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.service.ChargementService;
import sn.dscom.backend.service.ConnectedUtilisateurService;
import sn.dscom.backend.service.converter.UtilisateurConverter;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;
import sn.dscom.backend.service.interfaces.*;

import java.io.*;
import java.util.*;
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

    /**
     * chargement Service
     */
    @Autowired
    private IChargementService chargementService;

    /**
     * exploitation Service
     */
    @Autowired
    private IProduitService produitService;

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
    @Autowired
    private ISiteService siteService;

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

        log.info(" entete du fichier ");
        if (file.isEmpty()) {
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_FiLE_NOT_FOUND);
        }
        try{
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReader(reader) ;
            header = tabToList(csvReader.readNext());

            log.info(" entete du fichier "+header);
        }catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_INATTENDUE);
        }

        // la liste épurée des colonnes à mapper
       List<String> colonnesToMap = header.stream()
                .filter(Predicates.compose(colonnesToIgnore::contains, Functions.identity()).negate())
                .toList();


        FileInfoDTO fileInfoDTO = FileInfoDTO.builder()
                .enteteFile(colonnesToMap)
                .colonneTable(Arrays.asList(environment.getProperty("list.table.colonne").split(",")))
                .build();
        return  ResponseEntity.ok(fileInfoDTO);
    }

    /**
     * get header
     *
     * @param file transporteurDTO
     * @return l'entete
     */
    @PostMapping(path = "/upload")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<Long> uploadFile(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("mapEntete") String mapEnteteJson,
                                                   @RequestParam("nom") String nomDepot)
            throws IOException {

        // Utilisateur
        UtilisateurDTO utilisateurDTO = UtilisateurConverter.toUtilisateurDTO(connectedUtilisateurService.getConnectedUtilisateur());
        // La liste des produits en base
        List<ProduitDTO> produitDTOS = this.produitService.rechercherProduits().get();
        // Mapper
        ObjectMapper objectMapper = new ObjectMapper();
        // la liste des chargements à effectuer
        List<ChargementDTO> listChargementAEffectuer = new ArrayList<>();
        // Le Depot
        DepotDTO depot = new DepotDTO();
        String nom;
        SiteDTO siteDTO = null;

        // Controle sur le fichier
        if (file.isEmpty()) {
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_FiLE_NOT_FOUND);
        }

        try {
            Map<String, String> mapDatabaseEnteteFile = objectMapper.readValue(mapEnteteJson,new TypeReference<Map<String, String>>() {});
            nom = objectMapper.readValue(nomDepot, new TypeReference<String>() {});
            Map<String, String> mapInverse = new HashMap<>();
            //TODO: à enlever
            mapDatabaseEnteteFile.forEach((k, v) -> {
                mapInverse.put(v,k);
            });
            // enregister le depot
            depot = this.depotService.enregistrerDepot(buildDepot(file, utilisateurDTO, nom, StatutEnum.ENCOURS.getCode())).get();
            List<String> header = null;
            try{
                Reader reader = new InputStreamReader(file.getInputStream());
                CSVReader csvReader = new CSVReader(reader) ;
                header = tabToList(csvReader.readNext());
                // next Line
                String [] nextLine;

                // Chaque ligne du fichier est un chargement
                while ((nextLine = csvReader.readNext()) != null)
                {
                    // Ligne de chargement
                    List<String> chargement = tabToList(nextLine);
                    // rechercher Site
                    if(null == siteDTO) {
                        siteDTO = this.rechercherSite(chargement, mapInverse, header);
                    }

                    // On recupère le produit dans le chagement: On fait un chargement que pour les produit qui existe
                    String nomProduit = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.produit.nom")))).toUpperCase();
                    Optional<ProduitDTO> produitDTO = produitDTOS.stream().filter(produit -> nomProduit.equals(produit.getNomSRC())).findFirst();

                    if(produitDTO.isPresent()){
                        //Chargement d'une ligne du fichier
                        ChargementDTO chargementDTO = this.chargementService.effectuerChargement(chargement, mapInverse, header, depot);
                        listChargementAEffectuer.add(chargementDTO);
                    }else{
                        DepotController.log.info(String.format("Le %s n'existe pas dans le référentiel.", nomProduit));
                    }
                }

                // On modifie le depot avec la liste des chargements et l'heure de fin
                depot.setChargementDTOList(listChargementAEffectuer);
                depot.setDateHeureFinDepot( new Date());
                depot.setStatut(StatutEnum.SUCCES.getCode());
                depot.setSite(siteDTO);
                this.depotService.enregistrerDepot(depot);
                log.info(" entete du fichier "+header);
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
                .nbChargementReDeposes(1)
                .nomFichier(file.getName())
                .nbChargementErreur(0)
                .dateHeureDepot(new Date())
                .deposeur(utilisateurDTO)
                .build();
    }

    /**
     * uplaod file
     *
     * @param depotDTO transporteurDTO
     * @return le transporteur
     */
    @PostMapping(path = "/uploadFile")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<DepotDTO> deposer(@RequestBody DepotDTO depotDTO) {
        // Controle du fichier
        DepotDTO depot = new DepotDTO();
        File file = depotDTO.getFile();
       // File file = new File("C:\\Users\\diome\\Downloads\\BDD SEPTEMBRE 2021 OUROSSOGUI.csv");
        try(CSVReader reader
                    = new CSVReader(new FileReader(file)))
        {
            String [] nextLine;
            String[] header = reader.readNext();

            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
                //reader
                System.out.println(Arrays.toString(nextLine));
            }
        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        //2) enregistrer le depot

        //3) faire le traitement sur le fichier


        //enregistrer ou modifier Depot
        return  ResponseEntity.ok(depot);
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

    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<Boolean> supprimerDepot(@RequestBody DepotDTO depotDTO) {
        //supprimer Depot
        return  ResponseEntity.ok(depotService.supprimerDepot(depotDTO).booleanValue());
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
    private SiteDTO rechercherSite(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header){

        // Table SITE: nom et localité db_site_nom et db_site_localite
        //REFERIENTIEL: nom -> site sur fichier et alimenter la localité
        String siteName = ligneChargement.get(header.indexOf(mapCorrespondance.get(this.environment.getProperty("db.site.nom"))));

        return Iterables.getOnlyElement(Try.of(() -> SiteDTO.builder()
                        .nom(siteName.trim().toUpperCase())
                        .build())
                .mapTry(this.siteService::rechercherSite)
                .onFailure(e -> DepotController.log.error(String.format("Erreur lors de la recherche du Site : %s",e.getMessage())))
                .get().get());
    }

}
