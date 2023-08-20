package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
     *
     * @param utilisateurDTO
     * @return
     */
    @PostMapping (value = "/utilisateur/enregistrer")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public UtilisateurDTO enregistrerUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        return utilisateurService.sauvegarderUtilisateur(utilisateurDTO);
    }

    /**
     *
     * @param email
     * @return
     */
    @GetMapping(value = "/utilisateur/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public UtilisateurDTO chargerUtilisateurParMail(@PathVariable String email) {
        return utilisateurService.chargerUtilisateur(email);
    }

    /**
     *
     * @return
     */
    @GetMapping(value = "/utilisateur/users")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public List<UtilisateurDTO> chargerUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/utilisateur/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULT','EDIT')")
    public UtilisateurDTO chargerUtilisateurParId(@PathVariable Long id) {
        return utilisateurService.chargerUtilisateurParId(id);
    }

    /**
     *
     * @param id
     * @param email
     * @return
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
     *
     * @param utilisateurDTO
     * @return
     */
    @PutMapping (value = "/utilisateur/enregistrer")
    @PreAuthorize("hasAnyRole('ADMIN','EDIT')")
    public UtilisateurDTO sauvegarderUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        return utilisateurService.sauvegarderUtilisateur(utilisateurDTO);
    }
}
