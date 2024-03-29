package sn.dscom.backend.controller;

import com.google.common.base.Strings;
import com.google.common.collect.MoreCollectors;
import cyclops.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.service.interfaces.IChargementService;
import sn.dscom.backend.service.interfaces.IExploitationService;
import sn.dscom.backend.service.interfaces.ISiteService;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité de chargement
 * @version 1
 */
@RestController
@RequestMapping("api/v1/chargement")
public class ChargementController {

    /** Logger Factory */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChargementController.class);

    /**
     * chargement Service
     */
    @Autowired
    private IChargementService chargementService;

    /**
     * Exploitation Service
     */
    @Autowired
    private IExploitationService exploitationService;

    /**
     * Site Service
     */
    @Autowired
    private ISiteService siteService;

    /**
     * enregistrer Chargement
     * @param chargementDTO chargementDTO
     * @return la liste
     */
    @PostMapping(path = "/enregistrer")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<ChargementDTO> enregistrerChargement(@RequestBody ChargementDTO chargementDTO) {
        ChargementController.LOGGER.info("ChargementController: enregistrerChargement: ");
        return ResponseEntity.ok(this.chargementService.enregistrerChargement(chargementDTO).get());
    }

    /**
     * enregistrer Chargement
     * @param chargementDTO chargementDTO
     * @return la liste
     */
    @PutMapping (path = "/modifier")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<ChargementDTO> modifierChargement(@RequestBody ChargementDTO chargementDTO) {
        ChargementController.LOGGER.info("ChargementController: modifierChargement: ");
        return ResponseEntity.ok(this.chargementService.modifierChargement(chargementDTO));
    }

    /**
     * rechercher Chargement
     * @return la liste
     */
    @GetMapping(path = "/rechercher")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<ChargementDTO>> rechercherChargements() {
        ChargementController.LOGGER.info("ChargementController: rechercherChargements: ");
        Optional<List<ChargementDTO>> list = this.chargementService.rechercherChargements();

        // Appel du service rechercherSitesExploitation
        // si vide on retour une erreur 404
        return Try.withCatch(list::get)
                .peek(listChargementDTO -> ChargementController.LOGGER.info(String.format("ChargementController -> rechercherChargements: %s", listChargementDTO.size())))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * rechercher Chargement
     * @return la liste
     */
    @PostMapping(path = "/rechercherBy")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<ChargementDTO>> rechercherChargements(@RequestBody ChargementDscom theChargementAChercher) {
        ChargementController.LOGGER.info("ChargementController: rechercherChargements: ");
        Optional<List<ChargementDTO>> list = this.chargementService.rechercherChargements(buildChargementDTOArechercher(theChargementAChercher));

        // Appel du service rechercherSitesExploitation
        // si vide on retour une erreur 404
        return Try.withCatch(list::get)
                .peek(listChargementDTO -> ChargementController.LOGGER.info(String.format("ChargementController -> rechercherChargements: %s", listChargementDTO.size())))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     *build ChargementDTO A rechercher
     * @param theChargementAChercher theChargementAChercher
     * @return Chargement A Chercher
     */
    private ChargementDTO buildChargementDTOArechercher(ChargementDscom theChargementAChercher) {
        return ChargementDTO.builder()
                .destination(theChargementAChercher.getDestination())
                .exploitation(getExploitation(theChargementAChercher))
                .site(getSite(theChargementAChercher))
                .datePesage(theChargementAChercher.getDatePesage())
                .build();
    }

    /**
     * get Site
     * @param theChargementAChercher theChargementAChercher
     * @return SiteDTO
     */
    private SiteDTO getSite(ChargementDscom theChargementAChercher) {

        if (Strings.isNullOrEmpty(theChargementAChercher.getSiteDePesage()))
            return null;

        Optional<List<SiteDTO>> liste = siteService.rechercherSite(SiteDTO.builder()
                                                                        .nom(theChargementAChercher.getSiteDePesage())
                                                                        .build());
        return io.vavr.control.Try.of(liste::get)
                .get()
                .stream()
                .collect(MoreCollectors.onlyElement());
    }

    /**
     * get Exploitation
     * @param theChargementAChercher theChargementAChercher
     * @return ExploitationDTO
     */
    private ExploitationDTO getExploitation(ChargementDscom theChargementAChercher) {

        if (Strings.isNullOrEmpty(theChargementAChercher.getOrigine()))
            return null;

        Optional<List<ExploitationDTO>> liste = this.exploitationService.rechercherSiteExploitation(ExploitationDTO.builder()
                .nom(theChargementAChercher.getOrigine())
                .build());

        return io.vavr.control.Try.of(liste::get)
                .get()
                .stream()
                .collect(MoreCollectors.onlyElement());
    }

    @PostMapping(path = "/rechercheBy")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<Page<ChargementDTO>> rechercherChargements(@RequestBody CritereRecherche<?> critereRecherche) {
        ChargementController.LOGGER.info("ChargementController: rechercherChargements: ");
        return ResponseEntity.ok(chargementService.rechargementParCritere(critereRecherche));
    }

    /**
     * export Document
     * @param critereRecherche critereRecherche
     * @return FichierDTO
     * @throws UnsupportedEncodingException l'exception
     */
    @PostMapping("/exportDocument")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<FichierDTO> downloadDocument(@RequestBody CritereRecherche<?> critereRecherche) throws UnsupportedEncodingException {

        ChargementController.LOGGER.info("downloadDocument: exportDocument");
        List<ChargementDTO> datas = chargementService.rechercherChargementParCritere(critereRecherche);

        return ResponseEntity.ok(FichierDTO.builder()
                .content(this.chargementService.chargementDTOsToBytes(datas))
                .nom("Fichier-chargements.csv")
                .build()
        );


    }

    /**
     *
     * @param list list
     * @return FichierDTO
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    @PostMapping("/exportDocumentByIDChargement")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<FichierDTO> downloadDocumentByID(@RequestBody List<ChargementDTO> list) throws UnsupportedEncodingException {

        ChargementController.LOGGER.info("downloadDocumentByID: exportDocument");
        return ResponseEntity.ok(FichierDTO.builder()
                .content(this.chargementService.chargementDTOsToBytes(list))
                .nom("Fichier-chargements.csv")
                .build()
        );


    }
    /**
     * rechercheById
     * @param id id
     * @return ChargementDTO
     */
    @GetMapping(value = "/rechercheById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<ChargementDTO> chargerChargementParId(@PathVariable Long id) {
        return ResponseEntity.ok(chargementService.chargerChargementParId(id));
    }

    /**
     * supprimerChargement par critère
     * @param critereRecherche critereRecherche
     * @return true or false
     */
    @DeleteMapping(value = "/supprimerBycritere")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<Boolean> supprimerChargement(@RequestBody CritereRecherche critereRecherche) {
        return ResponseEntity.ok(chargementService.supprimerChargementBycritere(critereRecherche));
    }

    /**
     * supprimerChargement Par Id
     * @param chargementDTO chargementDTO
     * @return true or false
     */
    @DeleteMapping(value = "/supprimerById")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<Boolean> supprimerChargementParId(@RequestBody List<ChargementDTO> chargementDTO) {
        return ResponseEntity.ok(chargementService.supprimerChargementParId(chargementDTO));
    }
}
