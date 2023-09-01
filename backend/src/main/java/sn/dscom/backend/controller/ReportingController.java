package sn.dscom.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.BilanDTO;
import sn.dscom.backend.common.util.ChargementUtils;
import sn.dscom.backend.service.interfaces.IReportingService;

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
    private IReportingService reportingService;

    /**
     * rechercher Reporting Chargemnet
     * @return le bilan
     */
    @PostMapping(path = "/chargementByRegion")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<BilanDTO> rechercherReportingChargementByRegion(@RequestParam("annee") int annee) {
        ReportingController.LOGGER.info("ReportingController: rechercherReportingChargementByRegion");
        BilanDTO bilan = reportingService.rechercherReportingChargementByRegion(
                ChargementUtils.getDateDebutAnnee(String.valueOf(annee)),
                ChargementUtils.getDateFinAnnee(String.valueOf(annee))
        );
        return ResponseEntity.ok(bilan);
    }

    /**
     * rechercher Reporting Chargemnet
     * @return le bilan
     */
    @GetMapping(path = "/recouvrementAnnuel")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<BilanDTO> recouvrementProduitParAnnee() {
        ReportingController.LOGGER.info("ReportingController: recouvrementProduitParAnnee");
        BilanDTO bilan = reportingService.getRecouvrementProduitParAnne();

        return ResponseEntity.ok(bilan);
    }

    /**
     * rechercher Reporting Chargemnet
     * @return le bilan
     */
    @PostMapping(path = "/produitParAn")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<BilanDTO> reportingProduitByYear(@RequestParam("annee") int annee) {
        ReportingController.LOGGER.info("ExploitationController: rechercherSitesExploitation");
        BilanDTO bilan = reportingService.reportingProduitByYear(
                ChargementUtils.getDateDebutAnnee(String.valueOf(annee)),
                ChargementUtils.getDateFinAnnee(String.valueOf(annee))
        );
        return ResponseEntity.ok(bilan);
    }
}
