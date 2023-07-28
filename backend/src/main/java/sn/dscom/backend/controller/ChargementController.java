package sn.dscom.backend.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.service.interfaces.IChargementService;

import java.util.List;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité de chargement
 * @version 1
 */
@Log4j
@RestController
@RequestMapping("api/v1/chargement")
public class ChargementController {

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
        return ResponseEntity.ok(this.chargementService.enregistrerChargement(chargementDTO).get());
    }

    /**
     * rechercher Chargement
     * @return la liste
     */
    @GetMapping(path = "/rechercher")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<ChargementDTO>> rechercherChargements() {
        return  ResponseEntity.ok(this.chargementService.rechercherChargements().get());
    }
}
