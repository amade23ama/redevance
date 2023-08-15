package sn.dscom.backend.controller;

import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.service.interfaces.ITransporteurService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité de sur les transporteurs
 * @version 1
 */
@RestController
@RequestMapping("api/v1/transporteur")
public class TransporteurController {

    /** Logger Factory */
    private static final Logger LOGGER = LoggerFactory.getLogger(TransporteurController.class);

    /**
     * transporteur Service
     */
    @Autowired
    private ITransporteurService transporteurService;

    /**
     * enregistrer Transporteur
     *
     * @param transporteurDTO transporteurDTO
     * @return le transporteur
     */
    @PostMapping(path = "/enregistrer")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<TransporteurDTO> enregistrerTransporteur(@RequestBody TransporteurDTO transporteurDTO) {
        TransporteurController.LOGGER.info("TransporteurController: enregistrerTransporteur: ");
        return ResponseEntity.ok(this.transporteurService.enregistrerTransporteur(transporteurDTO).get());
    }

    /**
     * rechercher Transporteur
     * @param id TransporteurDTO
     * @return la liste avec une aucurence
     */
    @GetMapping(path = "/rechercherById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<TransporteurDTO>> rechercherTransporteur(@PathVariable long id) {
        TransporteurController.LOGGER.info("TransporteurController: rechercherTransporteur: ");
        return  ResponseEntity.ok(this.transporteurService.rechercherTransporteur(TransporteurDTO.builder().id(id).build()).get());
    }

    /**
     * rechercher Transporteurs
     * @return la liste
     */
    @GetMapping(path = "/rechercher")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<TransporteurDTO>> rechercherTransporteurs() {
        TransporteurController.LOGGER.info("TransporteurController: rechercherTransporteurs: ");
        return  ResponseEntity.ok(this.transporteurService.rechercherTransporteurs().get());
    }

    /**
     * supprimer Transporteur
     *
     * @param id id de l'entité
     * @return true si supprimé
     */
    //UPDATE ou DELETE sur la table « site » viole la contrainte de clé étrangère « fk_SITE » de la table « chargement »
    // @DeleteMapping(path = "/supprimer/{id}") // 80/api/v1/site/supprimer/2' from origin 'http://localhost:4200' has been blocked by CORS policy:
    @DeleteMapping(path = "/supprimer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> supprimerTransporteur(@PathVariable long id) {
        TransporteurController.LOGGER.info("TransporteurController: supprimerTransporteur: ");
        return  ResponseEntity.ok(this.transporteurService.supprimerTransporteur(TransporteurDTO.builder().id(id).build()));
    }

    /**
     * Compteur Transporteurs
     *
     * @return le nombre de Transporteur
     */
    @GetMapping(path = "/compter")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<Integer> getCompteurTransporteurs() {
        TransporteurController.LOGGER.info("TransporteurController: Compter les Transporteurs: ");
        return  ResponseEntity.ok(this.transporteurService.compterTransporteurs(LocalDateTime.now()));
    }


}
