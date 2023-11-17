package sn.dscom.backend.controller;

import cyclops.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;
import sn.dscom.backend.service.interfaces.ICategorieService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité de sur les Categories de véhicule
 * @version 1
 */
@RestController
@RequestMapping("api/v1/categorie")
public class CategorieController {

    /** Logger Factory */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategorieController.class);

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
    public ResponseEntity<CategorieDTO> enregistrerCategorie(@RequestBody CategorieDTO categorieDTO) throws DscomTechnicalException {
        CategorieController.LOGGER.info("CategorieController: Enregistrer Categorie: ");
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
        CategorieController.LOGGER.info("CategorieController: rechercherCategorie: ");
        //Appel du service rechercherCatégorie
        // si vide on renvoit 404
        return Try.withCatch(() -> this.categorieService.rechercherCategorie(CategorieDTO.builder().id(id).build()).get())
                .peek(listCategorie -> CategorieController.LOGGER.info(String.format("CategorieController: rechercherCategorie: %s", listCategorie.size())))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * rechercher Categories
     * @return la liste
     */
    @GetMapping(path = "/rechercher")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<CategorieDTO>> rechercherCategories() {
        CategorieController.LOGGER.info("CategorieController: rechercherCategories: ");

        //Appel du service rechercherCatégories
        // si vide on renvoit 404
        return Try.withCatch(() -> this.categorieService.rechercherCategories().get())
                .peek(listCategorie -> CategorieController.LOGGER.info(String.format("CategorieController: rechercherCategories: %s", listCategorie.size())))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
        CategorieController.LOGGER.info("CategorieController: supprimerCategorie: ");
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
        CategorieController.LOGGER.info("CategorieController: Compter les categories: ");
        return  ResponseEntity.ok(this.categorieService.compterCategorie(LocalDateTime.now()));
    }
    @PostMapping (value = "/rechercheBy")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<Page<CategorieDTO>> rechargementParCritere(@RequestBody CritereRecherche<?> critereRecherche) {
        return ResponseEntity.ok(this.categorieService.rechargementParCritere(critereRecherche));
    }
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public CategorieDTO chargerCategorieDTOParId(@PathVariable Long id) {
        return this.categorieService.chargerCategorieDTOParId(id);
    }

}
