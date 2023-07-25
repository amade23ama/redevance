package sn.dscom.backend.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.service.interfaces.ICategorieService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité de sur les Categories de véhicule
 * @version 1
 */
@Log4j
@RestController
@RequestMapping("api/v1/categorie")
public class CategorieController {

    /**
     * transporteur Service
     */
    @Autowired
    private ICategorieService categorieService;

    /**
     * enregistrer Categorie
     *
     * @param categorieDTO categorieDTO
     * @return le transporteur
     */
    @PostMapping(path = "/enregistrer")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<CategorieDTO> enregistrerCategorie(@RequestBody CategorieDTO categorieDTO) {

        return ResponseEntity.ok(this.categorieService.enregistrerCategorie(categorieDTO).get());
    }

    /**
     * rechercher Categorie
     * @param id CategorieDTO
     * @return la liste avec une aucurence
     */
    @GetMapping(path = "/rechercherById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<CategorieDTO>> rechercherCategorie(@PathVariable long id) {
        return  ResponseEntity.ok(this.categorieService.rechercherCategorie(CategorieDTO.builder().id(id).build()).get());
    }

    /**
     * rechercher Categories
     * @return la liste
     */
    @GetMapping(path = "/rechercher")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<CategorieDTO>> rechercherCategories() {
        return  ResponseEntity.ok(this.categorieService.rechercherCategories().get());
    }

    /**
     * supprimer Categorie
     *
     * @param id id de l'entité
     * @return true si supprimé
     */
    //UPDATE ou DELETE sur la table « site » viole la contrainte de clé étrangère « fk_SITE » de la table « chargement »
    // @DeleteMapping(path = "/supprimer/{id}") // 80/api/v1/site/supprimer/2' from origin 'http://localhost:4200' has been blocked by CORS policy:
    @DeleteMapping(path = "/supprimer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> supprimerCategorie(@PathVariable long id) {
        return  ResponseEntity.ok(this.categorieService.supprimerCategorie(CategorieDTO.builder().id(id).build()));
    }

    /**
     * Compteur Categorie
     *
     * @return le nombre de Categorie
     */
    @GetMapping(path = "/compter")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<Integer> getCompteurCategories() {
        log.info("Compter les categories");
        return  ResponseEntity.ok(this.categorieService.compterCategorie(LocalDateTime.now()));
    }
}
