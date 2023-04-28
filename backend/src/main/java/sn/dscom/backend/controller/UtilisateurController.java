package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.service.UtilisateurService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UtilisateurController {
    private UtilisateurService utilisateurService;
    @Autowired
    UtilisateurController(UtilisateurService utilisateurService){
        this.utilisateurService=utilisateurService;
    }

    //@ApiOperation(value = "Enregistrer un utilisateur")
    @PutMapping(value = "/utilisateur/enregistrer")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
   /* @ApiResponses(value = {@ApiResponse(code = 200, message = "Succès de la requête"),
            @ApiResponse(code = 401, message = "Ressource non autorisée"),
            @ApiResponse(code = 403, message = "Ressource interdite"),
            @ApiResponse(code = 404, message = "Ressource non trouvée"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")})
    */
    public UtilisateurDTO enregistrerUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        return utilisateurService.sauvegarderUtilisateur(utilisateurDTO);
    }
}
