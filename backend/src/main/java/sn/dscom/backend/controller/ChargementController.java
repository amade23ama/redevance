package sn.dscom.backend.controller;

import cyclops.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.service.interfaces.IChargementService;

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
}
