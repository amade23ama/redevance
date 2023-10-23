package sn.dscom.backend.controller;

import cyclops.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.service.interfaces.IVoitureService;

import java.util.List;
import java.util.Optional;

/**
 * Controlleur REST exsposant les services véhicule
 */
@RestController
@RequestMapping("/api/v1/vehicule")
public class VehiculeController {

    /** Logger Factory */
    private static final Logger LOGGER = LoggerFactory.getLogger(VehiculeController.class);

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
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<VehiculeDTO> enregistrerVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        VehiculeController.LOGGER.info("TransporteurController: enregistrerVehicule: ");
        return ResponseEntity.ok(this.voitureService.enregistrerVehicule(vehiculeDTO).get());
    }

    /**
     * Permet de modifier un véhicule en base
     * @param vehiculeDTO vehiculeDTO
     * @return le véhicule modifié
     */
    @PutMapping(path = "/modifier", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<VehiculeDTO> modifierVehicule(@RequestBody VehiculeDTO vehiculeDTO) {
        VehiculeController.LOGGER.info(String.format("Modification de l'entité voiture d'identifiant: %s", vehiculeDTO.getId()));
        return ResponseEntity.ok(this.voitureService.modifierVehicule(vehiculeDTO).get());
    }

    /**
     * Permet de supprimer un véhicule en base avec le parametre souhaité
     * @param vehiculeDTO vehiculeDTO
     * @return true l'entité est supprimé
     */
    @PostMapping(path = "/supprimer", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> supprimerVehicule(@RequestBody VehiculeDTO vehiculeDTO) {

        // Appel sur service pour supprimer
        VehiculeController.LOGGER.info(String.format("Suppression de l'entité voiture d'identifiant: %s", vehiculeDTO.getId()));
        this.voitureService.supprimerVehicule(vehiculeDTO);
        return ResponseEntity.ok(true);
    }

    /**
     * Permet de rechercher la liste des véhicules en base
     * @return la liste des véhicules
     */
    @GetMapping(path = "/rechercher", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<VehiculeDTO>> rechercherVehicules() {

        //Appel du service rechercherVehicules de voitureService
        Optional<List<VehiculeDTO>> list = this.voitureService.rechercherVehicules();

        // si on trouve au moins une donnée à la retour
        if (list.isPresent()){
            VehiculeController.LOGGER.info("rechercherVehicules");
            //Appel du service rechercherVehicules
            // si vide on renvoit 404
            return Try.withCatch(list::get)
                    .peek(listVoiture -> VehiculeController.LOGGER.info(String.format("VehiculeController: rechercherVehicules: %s", listVoiture.size())))
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        // sinon on léve une exception: 404 Not Found.
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public VehiculeDTO chargerVehiculeDTOParId(@PathVariable Long id) {
        return this.voitureService.chargerVehiculeDTOParId(id);
    }

    /**
     * rechargementParCritere
     * @param critereRecherche critereRecherche
     * @return la list
     */
    @PostMapping (value = "/rechercheBy")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<VehiculeDTO>> rechargementParCritere(@RequestBody CritereRecherche<?> critereRecherche) {
        return ResponseEntity.ok(this.voitureService.rechargementParCritere(critereRecherche));
    }
}
