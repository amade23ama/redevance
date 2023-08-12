package sn.dscom.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.service.interfaces.IProduitService;

import java.util.List;

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
        return ResponseEntity.ok(produitService.rechercherProduits().get());
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
}

