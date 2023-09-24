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
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.service.interfaces.IProduitService;

import java.util.List;
import java.util.Optional;

/**
 * @apiNote Controlleur REST exsposant les services produit
 * @author diome
 */
@RestController
@RequestMapping("/api/v1/produit")
public class ProduitController {

    /** Logger Factory */
    private static final Logger logger = LoggerFactory.getLogger(ProduitController.class);

    /**
     * produitService
     */
    @Autowired
    IProduitService produitService;

    /**
     * Permet de rechercher la liste des Produits en base
     *
     * @return la liste des produits
     */
    @GetMapping(path = "/rechercher", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<ProduitDTO>> rechercherProduits() {
        ProduitController.logger.info("Rechercher Produits");
        Optional<List<ProduitDTO>> list = produitService.rechercherProduits();

        // Appel du service rechercherProduits
        // si vide on retour une erreur 404
        return Try.withCatch(list::get)
                .peek(listProduit -> ProduitController.logger.info(String.format("ProduitController -> Rechercher Produits: %s", listProduit.size())))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Permet de enregistrer et de modifier le Produit en base
     *
     * @param produitDTO le produit à enregister
     * @return l'entité
     */
    @PostMapping(path = "/enregistrer", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<ProduitDTO> enregistrerProduit(@RequestBody ProduitDTO produitDTO) {
        ProduitController.logger.info("Enregistrer Produits");
        return ResponseEntity.ok(produitService.enregistrerProduit(produitDTO).get());
    }

    /**
     * Permet de supprimer un Produit en base
     *
     * @param id id produit à supprimer
     * @return l'entité
     */
    @DeleteMapping(path = "/supprimer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<Boolean> supprimerProduit(@PathVariable("id") Long id) {
        ProduitController.logger.info("supprimer Produits");
        return ResponseEntity.ok(produitService.supprimerProduit(id));
    }

    /**
     * Compteur Produits
     *
     * @return le nombre de produit
     */
    @GetMapping(path = "/compter", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<Integer> getCompteurProduits() {
        ProduitController.logger.info("compter Produits");
        return ResponseEntity.ok(produitService.compterProduit());
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ProduitDTO chargerProduitParId(@PathVariable Long id) {
        return produitService.chargerProduitParId(id);
    }

    /**
     * rechargementParCritere
     * @param critereRecherche critereRecherche
     * @return la list
     */
    @PostMapping (value = "/rechercheBy")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<ProduitDTO>> rechargementParCritere(@RequestBody CritereRecherche<?> critereRecherche) {
        return ResponseEntity.ok(produitService.rechargementParCritere(critereRecherche));
    }
}

