package sn.dscom.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.service.ConnectedUtilisateurService;
import sn.dscom.backend.service.converter.UtilisateurConverter;
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
     * chargement Service
     */
    @Autowired
    private IChargementService chargementService;

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
     * get header
     *
     * @return l'entete
     */
    @PostMapping(path = "/fileHeader")
    //@PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<FileInfoDTO> getFileHeader(@RequestParam("file") MultipartFile file){
        List<String> header = null;
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
        FileInfoDTO fileInfoDTO = FileInfoDTO.builder()
                .enteteFile(header)
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
        // Mapper
        ObjectMapper objectMapper = new ObjectMapper();
        // Le Depot
        DepotDTO depot = new DepotDTO();
        String nom;

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
            depot = this.depotService.enregistrerDepot(buildDepot(file, utilisateurDTO, nom)).get();
            List<String> header = null;
            try{
                Reader reader = new InputStreamReader(file.getInputStream());
                CSVReader csvReader = new CSVReader(reader) ;
                header = tabToList(csvReader.readNext());

                // next Line
                String [] nextLine;

                while ((nextLine = csvReader.readNext()) != null)
                {
                    List<String> chargement = tabToList(nextLine);
                    ChargementDTO chargementDTO = this.chargementService.effectuerChargement(chargement, mapInverse, header, depot);
                }
                log.info(" entete du fichier "+header);
            }catch (IOException | CsvValidationException e) {
                e.printStackTrace();
                throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_INATTENDUE);
            }

            // todo
        } catch (Exception e) {
            e.printStackTrace();
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
    private static DepotDTO buildDepot(MultipartFile file, UtilisateurDTO utilisateurDTO, String nom) {
        return DepotDTO.builder()
                .nom(nom)
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

}
