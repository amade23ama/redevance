package sn.dscom.backend.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.ExploitationDTO;
import sn.dscom.backend.common.dto.FileInfoDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.service.interfaces.IDepotService;
import sn.dscom.backend.service.interfaces.IExploitationService;
import sn.dscom.backend.service.interfaces.ISiteService;

import java.io.*;
import java.util.*;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité de depot
 * @version 1
 */
@Log4j
@RestController
@RequestMapping("/api/v1/depot")
public class DepotController {

    /**
     * depot Service
     */
    @Autowired
    private IDepotService depotService;

    /**
     * site Service
     */
    @Autowired
    ISiteService siteService;

    /**
     * exploitation Service
     */
    @Autowired
    IExploitationService exploitationService;

    /**
     * get header
     *
     * @param depotDTO transporteurDTO
     * @return l'entete
     */
    @PostMapping(path = "/fileHeader")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<String[]> getFileHeader(@RequestBody DepotDTO depotDTO){
        // l'entete du fichier
        String[] header = null;
        File file = depotDTO.getFile();

        try(CSVReader reader = new CSVReader(new FileReader(file))) {
            header = reader.readNext();
            System.out.println(Arrays.toString(header));
        }catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        //enregistrer ou modifier Depot
        return  ResponseEntity.ok(header);
    }

    /**
     * get header
     *
     * @param file transporteurDTO
     * @return l'entete
     */
    @PostMapping(path = "/upload")
   // @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<FileInfoDTO> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Test Enregistrement
        /*this.depotService.enregistrerDepot(DepotDTO.builder()
                        .nom("depot1")
                        .nbChargementReDeposes(1)
                        .nomFichier(file.getName())
                        .nbChargementErreur(0)
                        .dateHeureDepot(new Date())
                        .deposeur(UtilisateurDTO.builder().id(1L).build())
                        .build());

        this.enregistrerSite(SiteDTO.builder()
                .nom("diofior".toUpperCase())
                .localite("diofior".toUpperCase())
                .dateCreation(new Date())
                .build());

        this.enregistrerExploitation(ExploitationDTO.builder()
                        .nom("fimela".toUpperCase())
                        .region("Fatick".toUpperCase())
                        .dateCreation(new Date())
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
               donneesFichier.add(nextLine);
            }
        }catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return  ResponseEntity.ok(FileInfoDTO.builder()
                .colonnesFile(header)
                .taille(file.getSize())
                .donneesFichier(donneesFichier)
                .colonneTable(new String[]{"nom", "date","provenance"})
                .build());
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
     * @param siteDTO siteDTO
     * @return l'objet enregisté
     */
    private SiteDTO enregistrerSite(SiteDTO siteDTO){
        //site: nom et localite
        return this.siteService.enregistrerSite(siteDTO).get();
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
}
