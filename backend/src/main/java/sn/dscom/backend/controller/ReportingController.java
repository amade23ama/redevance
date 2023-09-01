package sn.dscom.backend.controller;

import cyclops.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.common.dto.ExploitationDTO;
import sn.dscom.backend.common.dto.ReportingDTO;
import sn.dscom.backend.common.util.ChargementUtils;
import sn.dscom.backend.service.interfaces.IChargementService;
import sn.dscom.backend.service.interfaces.IReportingService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * ReportingController
 * @version 1
 * @author diome
 */
@RestController
@RequestMapping("/api/v1/reporting")
public class ReportingController {

    /** Logger Factory */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportingController.class);

    /**
     * chargement Service
     */
    @Autowired
    private IChargementService chargementService;

    /**
     * chargement Service
     */
    @Autowired
    private IReportingService reportingService;

    /**
     * rechercher Reporting Chargemnet
     * @return la liste
     */
    @PostMapping(path = "/chargementByRegion")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<ReportingDTO>> rechercherReportingChargementByRegion(@RequestParam("annee") int annee) {
        ReportingController.LOGGER.info("ReportingController: rechercherReportingChargementByRegion");
        List<ReportingDTO> liste = reportingService.rechercherReportingChargementByRegion(
                ChargementUtils.getDateDebutAnnee(String.valueOf(annee)),
                ChargementUtils.getDateFinAnnee(String.valueOf(annee))
        );
        return ResponseEntity.ok(liste);
    }

    /**
     * rechercher Reporting Chargemnet
     * @return la liste
     */
    @PostMapping(path = "/produitByExploitation")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<ReportingDTO>> reportingProduitByExploitation(@RequestParam("annee") int annee) {
        ReportingController.LOGGER.info("ReportingController: rechercherSitesExploitation");
        Optional<List<ChargementDTO>> list = this.chargementService.rechercherChargements();

        // Appel du service rechercherSitesExploitation
        // si vide on retour une erreur 404
        return ResponseEntity.ok(null);
    }

    /**
     * rechercher Reporting Chargemnet
     * @return la liste
     */
    @PostMapping(path = "/produitParAn")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<ReportingDTO>> reportingProduitByYear(@RequestParam("annee") int annee) {
        ReportingController.LOGGER.info("ExploitationController: rechercherSitesExploitation");
        List<ReportingDTO> liste = reportingService.reportingProduitByYear(
                ChargementUtils.getDateDebutAnnee(String.valueOf(annee)),
                ChargementUtils.getDateFinAnnee(String.valueOf(annee))
        );
        return ResponseEntity.ok(liste);
    }
}
