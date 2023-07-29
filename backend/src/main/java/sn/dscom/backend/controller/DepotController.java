package sn.dscom.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.service.interfaces.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité de depot
 * @version 1
 */
@Log4j
@RestController
@RequestMapping("/api/v1/depot")
public class DepotController {
   // static Logger log= LogManager.getLogger(DepotController.class);
    /**
     * depot Service
     */
    @Autowired
    private IDepotService depotService;

    /**
     * site Service
     */
    @Autowired
    private ISiteService siteService;

    /**
     * exploitation Service
     */
    @Autowired
    IExploitationService exploitationService;

    /**
     * transporteur Service
     */
    @Autowired
    private ITransporteurService transporteurService;

    /**
     * voiture Service
     */
    @Autowired
    private IVoitureService voitureService;

    /**
     * categorie Service
     */
    @Autowired
    private ICategorieService categorieService;

    /**
     * chargement Service
     */
    @Autowired
    private IChargementService chargementService;

    /**
     * produit Service
     */
    @Autowired
    private IProduitService produitService;



    /**
     * get header
     *
     * @return l'entete
     */
    @PostMapping(path = "/fileHeader")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<FileInfoDTO> getFileHeader(@RequestParam("file") MultipartFile file){
        List<String> header = null;
        log.info(" entete du fichier ");
        if (file.isEmpty()) {
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_FiLE_NOT_FOUND);
        }
        try{
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReader(reader) ;
            header = Arrays.stream((csvReader.readNext()))
                               .flatMap(line -> Arrays.stream(line.split(";")))
                                .collect(Collectors.toList());
            log.info(" entete du fichier "+header);
        }catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_INATTENDUE);
        }
        FileInfoDTO fileInfoDTO=FileInfoDTO.builder().enteteFile(header).build();
        return  ResponseEntity.ok(fileInfoDTO);
    }

    /**
     * get header
     *
     * @param file transporteurDTO
     * @return l'entete
     */
    @PostMapping(path = "/upload")
   // @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<Long> uploadFile(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("mapEntete") String mapEnteteJson,
                                                  @RequestParam("nom") String nomDepot)
                                                  throws IOException {
        log.info(" entete du fichier ");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> mapDatabaseEnteteFile = objectMapper.readValue(mapEnteteJson,new TypeReference<Map<String, Object>>() {});
            // todo
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_FiLE_NOT_FOUND);
        }
        if (file.isEmpty()) {
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_FiLE_NOT_FOUND);
        }

        // Test Enregistrement
       /* DepotDTO depot = this.enregistrerDepot(DepotDTO.builder()
                        .nom("depot1")
                        .nbChargementReDeposes(1)
                        .nomFichier(file.getName())
                        .nbChargementErreur(0)
                        .dateHeureDepot(new Date())
                        .deposeur(UtilisateurDTO.builder().id(1L).build())
                        .build());

        // Table SITE: nom et localité db_site_nom et db_site_localite
        SiteDTO site = this.enregistrerSite(SiteDTO.builder()
                .nom("diofior".toUpperCase())
                .localite("diofior".toUpperCase())
                .dateCreation(new Date())
                .build());

        ExploitationDTO exploitationDTO = this.enregistrerExploitation(ExploitationDTO.builder()
                        .nom("fimela".toUpperCase())
                        .region("Fatick".toUpperCase())
                        .dateCreation(new Date())
                .build());

        CategorieDTO cat = this.enregistrerCategorie(CategorieDTO.builder()
                        .type("TM2")
                        .volume(12.3)
                        .dateCreation(new Date())
                        .build());
        TransporteurDTO transp = this.enregistrerTransporteur(TransporteurDTO.builder()
                        .type("PP")
                        .nom("diop".toUpperCase())
                        .prenom("ibou".toUpperCase())
                        .adresse("dakar 12")
                        .email("test@test.fr")
                        .telephone("772345625")
                        .dateCreation(new Date())
                        .build());

        VehiculeDTO voiture = enregistrerVehicule(VehiculeDTO.builder()
                        .immatriculation("aa224bb".toUpperCase())
                        .dateCreation(new Date())
                        .categorie(cat)
                        .transporteur(transp)
                .build());

        this.enregistrerChargement(ChargementDTO.builder()
                .dateCreation(new Date())
                .datePesage(new Date())
                .poids(20.3)
                .ecart(21.03)
                .poidsMax(40.00)
                .poidsSubst(30.0)
                .destination("THIES")
                .volumeMoyen(60.3)
                .volumeSubst(50.2)
                .vehicule(voiture)
                .site(site)
                .depot(depot)
                .exploitation(exploitationDTO)
                .produit(this.produitService.rechercherProduits().get().stream().findFirst().get())
                .build());*/

        //TODO: Juste pour les tests
        String ApplicationPath =  "C:\\Users\\Public\\test"+".csv";

        // l'entete du fichier
        String[] header = null;
        List<String[]> donneesFichier = new ArrayList<>();
        File fileUploaded = new File(ApplicationPath);
        file.transferTo(fileUploaded);

        try(CSVReader reader = new CSVReader(new FileReader(ApplicationPath))) {
            header = reader.readNext();
            String [] nextLine;
            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
                Map<String, Map<String, Integer>> maps = new HashMap<>();
                Map<String, Integer> map = new HashMap<>();

                map.put("db_site_nom", 0);
                map.put("db_site_localite", 5);
                maps.put("site",map);

                // Table SITE: nom et localité db_site_nom et db_site_localite
                //SiteDTO site = this.enregistrerSite(nextLine, maps);

               donneesFichier.add(nextLine);
            }
        }catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        //return  ResponseEntity.ok(FileInfoDTO.builder()
               // .colonnesFile(header)
                //.taille(file.getSize())
                //.donneesFichier(donneesFichier)
                //.colonneTable(new String[]{"nom", "date","provenance"})
                //.build());
        // todo return numero de depot
        return   ResponseEntity.ok(2L);
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
        return  ResponseEntity.ok(this.enregistrerDepot(depotDTO));
    }

    /**
     * rechercher depots
     * @return la liste
     */
    @GetMapping(path = "/rechercher")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<DepotDTO>> rechercherDepot() {
        //rechercher all Depots
        return  ResponseEntity.ok(depotService.rechercherDepots().get());
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
        return  ResponseEntity.ok(depotService.rechercherDepotByCriteres(depotDTO).get());
    }

    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<Boolean> supprimerDepot(@RequestBody DepotDTO depotDTO) {
        //supprimer Depot
        return  ResponseEntity.ok(depotService.supprimerDepot(depotDTO).booleanValue());
    }

    /**
     * save en base
     *
     * @param depotDTO depotDTO
     * @return l'objet enregisté
     */
    private DepotDTO enregistrerDepot(DepotDTO depotDTO){
        return depotService.enregistrerDepot(depotDTO).get();
    }

    /**
     * save en base
     *
     * @param data siteDTO
     * @return l'objet enregisté
     */
    private SiteDTO enregistrerSite(String[] data, Map<String, Map<String, Integer>> maps){

        // Table SITE: nom et localité db_site_nom et db_site_localite

        //site: nom et localite
        return this.siteService.enregistrerSite(SiteDTO.builder()
                                                        .nom(data[maps.get("site").get("db_site_nom")].toUpperCase())
                                                        .localite(data[maps.get("site").get("db_site_localite")].toUpperCase())
                                                        .dateCreation(new Date())
                                                        .build()
                                                ).get();
    }

    /**
     * save en base
     *
     * @param exploitationDTO ExploitationDTO
     * @return l'objet enregisté
     */
    private ExploitationDTO enregistrerExploitation(ExploitationDTO exploitationDTO){
        //site: site_origine et region
        return this.exploitationService.enregistrerSiteExploitation(exploitationDTO).get();
    }

    /**
     * save en base
     *
     * @param categorieDTO categorieDTO
     * @return l'objet enregisté
     */
    private CategorieDTO enregistrerCategorie(CategorieDTO categorieDTO){
        //CATEGORIE:
        return this.categorieService.enregistrerCategorie(categorieDTO).get();
    }

    /**
     * save en base
     *
     * @param transporteurDTO transporteurDTO
     * @return l'objet enregisté
     */
    private TransporteurDTO enregistrerTransporteur(TransporteurDTO transporteurDTO){
        //TRANSPORTEUR: NOM_RAISON_SOCIALE,TELEPHONE
        return this.transporteurService.enregistrerTransporteur(transporteurDTO).get();
    }

    /**
     * save en base
     *
     * @param vehiculeDTO transporteurDTO
     * @return l'objet enregisté
     */
    private VehiculeDTO enregistrerVehicule(VehiculeDTO vehiculeDTO){
        //VEHICULE: transpoteur et categorie
        return this.voitureService.enregistrerVehicule(vehiculeDTO).get();
    }

    /**
     * save en base
     *
     * @param chargementDTO chargementDTO
     * @return l'objet enregisté
     */
    private ChargementDTO enregistrerChargement(ChargementDTO chargementDTO){
        //CHARGEMENT
        return this.chargementService.enregistrerChargement(chargementDTO).get();
    }
}
