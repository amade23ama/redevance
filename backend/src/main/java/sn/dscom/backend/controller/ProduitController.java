package sn.dscom.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.service.interfaces.IProduitService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @apiNote Controlleur REST exsposant les services produit
 * @author diome
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/produit")
public class ProduitController {

    /**
     * produitService
     */
    @Autowired
    IProduitService produitService;

    /**
     * Permet de rechercher la liste des Produits en base
     * @return la liste des produits
     */
    @GetMapping(path = "/rechercher", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<ProduitDTO>> rechercherProduits() {
        log.info("Rechercher Produits");
        return  ResponseEntity.ok(produitService.rechercherProduits().get());
    }

    /**
     * Permet de enregistrer et de modifier le Produit en base
     *
     * @param produitDTO le produit à enregister
     * @return l'entité
     */
    @PostMapping(path = "/enregistrer", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ProduitDTO> enregistrerProduit(@RequestBody ProduitDTO produitDTO) {
        log.info("Enregistrer Produits");
        return  ResponseEntity.ok(produitService.enregistrerProduit(produitDTO).get());
    }

    /**
     * Compteur Produits
     *
     * @return le nombre de produit
     */
    @GetMapping(path = "/compter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Integer> getCompteurProduits() {
        log.info("compter Produits");
        return  ResponseEntity.ok(produitService.compterProduit());
    }
}
