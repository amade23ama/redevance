package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.service.interfaces.IVoitureService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controlleur REST exsposant les services véhicule
 */
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
        return ResponseEntity.ok(VehiculeDTO.builder()
                                                .immatriculation("AA-123-DT")
                                                .build());
    }

    /**
     * Permet de modifier un véhicule en base
     * @param vehiculeDTO
     * @return le véhicule modifié
     */
    @PostMapping(path = "/modifier", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<VehiculeDTO> modifierVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        return ResponseEntity.ok(VehiculeDTO.builder().immatriculation("AA-123-DT").build());
    }

    /**
     * Permet de supprimer un véhicule en base avec l'id
     * @param id
     * @return
     */
    @DeleteMapping(path = "/supprimer/{id}")
    @ResponseBody
    public ResponseEntity<VehiculeDTO> supprimerVehicule( @RequestParam(name = "id", required = true) String id) {
        return ResponseEntity.ok(VehiculeDTO.builder().immatriculation("AA-123-DT").build());
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
            return ResponseEntity.ok(list.get());
        }

        // sinon on léve une exception: 404 Not Found.
        return ResponseEntity.notFound().build();
    }
}
