package sn.dscom.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.service.interfaces.IVoitureService;

import java.util.List;
import java.util.Optional;

/**
 * Controlleur REST exsposant les services véhicule
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/vehicule")
public class VehiculeController {

    /** voitureService */
    @Autowired
    private IVoitureService voitureService;

    /**
     * Permet de definir un véhicule en base
     * @param vehiculeDTO the vehiculeDTO
     * @return liste de vehicules trouvés
     */
    @PostMapping(path = "/enregistrer", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<VehiculeDTO> enregistrerVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        return ResponseEntity.ok(this.voitureService.enregistrerVehicule(vehiculeDTO).get());
    }

    /**
     * Permet de modifier un véhicule en base
     * @param vehiculeDTO
     * @return le véhicule modifié
     */
    @PostMapping(path = "/modifier", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<VehiculeDTO> modifierVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        log.info("Modification de l'entité voiture d'identifiant: {}", vehiculeDTO.getId());
        return ResponseEntity.ok(this.voitureService.modifierVehicule(vehiculeDTO).get());
    }

    /**
     * Permet de supprimer un véhicule en base avec le parametre souhaité
     * @param vehiculeDTO
     * @return true l'entité est supprimé
     */
    @PostMapping(path = "/supprimer")
    @ResponseBody
    public ResponseEntity<Boolean> supprimerVehicule(@RequestBody VehiculeDTO vehiculeDTO) {

        // Appel sur service pour supprimer
        log.info("Suppression de l'entité voiture d'identifiant: {}", vehiculeDTO.getId());
        this.voitureService.supprimerVehicule(vehiculeDTO);
        return ResponseEntity.ok(true);
    }

    /**
     * Permet de rechercher la liste des véhicules en base
     * @return la liste des véhicules
     */
    @GetMapping(path = "/rechercher")
    @ResponseBody
    public ResponseEntity<List<VehiculeDTO>> rechercherVehicules() {

        //Appel du service rechercherVehicules de voitureService
        Optional<List<VehiculeDTO>> list = this.voitureService.rechercherVehicules();

        // si on trouve au moins une donnée à la retour
        if (list.isPresent()){
            log.info("rechercherVehicules");
            return ResponseEntity.ok(list.get());
        }

        // sinon on léve une exception: 404 Not Found.
        return ResponseEntity.notFound().build();
    }
}
