package sn.dscom.backend.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.service.interfaces.ITransporteurService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité de sur les transporteurs
 * @version 1
 */
@Log4j
@RestController
@RequestMapping("api/v1/transporteur")
public class TransporteurController {

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
    public ResponseEntity<TransporteurDTO> enregistrerTransporteur(@RequestBody TransporteurDTO transporteurDTO) {

        return ResponseEntity.ok(this.transporteurService.enregistrerTransporteur(transporteurDTO).get());
    }

    /**
     * rechercher Transporteur
     * @param id TransporteurDTO
     * @return la liste avec une aucurence
     */
    @GetMapping(path = "/rechercherById/{id}")
    public ResponseEntity<List<TransporteurDTO>> rechercherTransporteur(@PathVariable long id) {
        return  ResponseEntity.ok(this.transporteurService.rechercherTransporteur(TransporteurDTO.builder().id(id).build()).get());
    }

    /**
     * rechercher Transporteurs
     * @return la liste
     */
    @GetMapping(path = "/rechercher")
    public ResponseEntity<List<TransporteurDTO>> rechercherTransporteurs() {
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
    public ResponseEntity<Boolean> supprimerTransporteur(@PathVariable long id) {
        return  ResponseEntity.ok(this.transporteurService.supprimerTransporteur(TransporteurDTO.builder().id(id).build()));
    }

    /**
     * Compteur Transporteurs
     *
     * @return le nombre de Transporteur
     */
    @GetMapping(path = "/compter")
    public ResponseEntity<Integer> getCompteurTransporteurs() {
        log.info("Compter les Transporteurs");
        return  ResponseEntity.ok(this.transporteurService.compterTransporteurs(LocalDateTime.now()));
    }


}
