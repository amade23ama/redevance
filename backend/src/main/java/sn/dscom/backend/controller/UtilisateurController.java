package sn.dscom.backend.controller;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.service.interfaces.IUtilisateurService;

import java.util.List;

/**
 * Utilisateur Controller
 */
@RestController
@RequestMapping("/api")
public class UtilisateurController {

    /** utilisateur Service */
    @Autowired
    private IUtilisateurService utilisateurService;

    /**
     * enregistrerUtilisateur
     * @param utilisateurDTO utilisateurDTO
     * @return utilisateurDTO
     */
    @PostMapping (value = "/utilisateur/enregistrer")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT','CONSULT')")
    public UtilisateurDTO enregistrerUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {

        // check des champs obligatoires
        Preconditions.checkNotNull(utilisateurDTO.getNom(), "Le Nom est obligatoir");
        Preconditions.checkNotNull(utilisateurDTO.getPrenom(), "Le Prenom est obligatoir");
        Preconditions.checkNotNull(utilisateurDTO.getEmail(), "Le Prenom est obligatoir");
        Preconditions.checkNotNull(utilisateurDTO.getTelephone(), "Le Telephone est obligatoir");

        return utilisateurService.sauvegarderUtilisateur(utilisateurDTO);
    }

    /**
     * chargerUtilisateurParMail
     * @param email email
     * @return UtilisateurDTO
     */
    @GetMapping(value = "/utilisateur/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public UtilisateurDTO chargerUtilisateurParMail(@PathVariable String email) {
        return utilisateurService.chargerUtilisateur(email);
    }

    /**
     * chargerUtilisateurs
     * @return la liste
     */
    @GetMapping(value = "/utilisateur/users")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public List<UtilisateurDTO> chargerUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    /**
     * chargerUtilisateurParId
     * @param id id
     * @return UtilisateurDTO
     */
    @GetMapping(value = "/utilisateur/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public UtilisateurDTO chargerUtilisateurParId(@PathVariable Long id) {
        return utilisateurService.chargerUtilisateurParId(id);
    }

    /**
     * checkEmail
     * @param id id
     * @param email email
     * @return true or false
     */
    @GetMapping(value = "/utilisateur/exist/{id}/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public boolean checkEmail(@PathVariable Long id,@PathVariable String email) {
        return utilisateurService.checkEmail(id,email);
    }

    /**
     * checkEmail
     * @param email email
     * @return boolean
     */
    @GetMapping(value = "/utilisateur/exist/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public boolean checkEmail(@PathVariable String email) {
        return utilisateurService.checkEmail(email);
    }

    /**
     * sauvegarderUtilisateur
     * @param utilisateurDTO utilisateurDTO
     * @return UtilisateurDTO
     */
    @PutMapping (value = "/utilisateur/enregistrer")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT','CONSULT')")
    public UtilisateurDTO sauvegarderUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        // check des champs obligatoires
        Preconditions.checkNotNull(utilisateurDTO.getNom(), "Le Nom est obligatoir");
        Preconditions.checkNotNull(utilisateurDTO.getPrenom(), "Le Prenom est obligatoir");
        Preconditions.checkNotNull(utilisateurDTO.getEmail(), "Le Prenom est obligatoir");
        Preconditions.checkNotNull(utilisateurDTO.getTelephone(), "Le Telephone est obligatoir");
        return utilisateurService.sauvegarderUtilisateur(utilisateurDTO);
    }
    @PostMapping (value = "/utilisateur/recherche")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public List<UtilisateurDTO> rechargementParCritere(@RequestBody CritereRecherche<?> critereRecherche) {
             return utilisateurService.rechargementParCritere(critereRecherche);
    }
}
