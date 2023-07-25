package sn.dscom.backend.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.service.interfaces.ISiteService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @apiNote Controller REST des opérations sur la fonctionnalité Site
 * @version 1
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/site")
public class SiteController {

    /** site Service */
    @Autowired
    ISiteService siteService;

    /**
     * enregistrer Site
     * @param siteDTO siteDTO
     * @return la liste
     */
    @PostMapping(path = "/enregistrer")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public ResponseEntity<SiteDTO> enregistrerSite(@RequestBody SiteDTO siteDTO) {
        return ResponseEntity.ok(this.siteService.enregistrerSite(siteDTO).get());
    }

    /**
     * rechercher Site
     * @param id siteDTO
     * @return la liste
     */
    @GetMapping(path = "/rechercherById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<SiteDTO>> rechercherSite(@PathVariable long id) {
        return  ResponseEntity.ok(this.siteService.rechercherSite(SiteDTO.builder().id(id).build()).get());
    }

    /**
     * rechercher Sites
     * @return la liste
     */
    @GetMapping(path = "/rechercher")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<List<SiteDTO>> rechercherSites() {
        return  ResponseEntity.ok(this.siteService.rechercherSites().get());
    }

    /**
     * supprimer Site
     *
     * @param id id de l'entité
     * @return true si supprimé
     */
    //UPDATE ou DELETE sur la table « site » viole la contrainte de clé étrangère « fk_SITE » de la table « chargement »
   // @DeleteMapping(path = "/supprimer/{id}") // 80/api/v1/site/supprimer/2' from origin 'http://localhost:4200' has been blocked by CORS policy:
    @GetMapping(path = "/supprimer/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Boolean> supprimerSite(@PathVariable long id) {
        return  ResponseEntity.ok(this.siteService.supprimerSite(SiteDTO.builder().id(id).build()));
    }

    /**
     * Compteur Sites
     *
     * @return le nombre de site
     */
    @GetMapping(path = "/compter")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public ResponseEntity<Integer> getCompteurSites() {
        log.info("Compter Site");
        return  ResponseEntity.ok(this.siteService.compterSite(LocalDateTime.now()));
    }
}
