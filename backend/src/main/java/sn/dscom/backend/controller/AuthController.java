package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.dscom.backend.common.TokenObject;
import sn.dscom.backend.common.dto.Credentials;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.service.ConnectedUtilisateurService;

@RestController
@RequestMapping("/api")
public class AuthController {

    private ConnectedUtilisateurService connectedUtilisateurService;
    @Autowired
    AuthController(ConnectedUtilisateurService connectedUtilisateurService){
         this.connectedUtilisateurService=connectedUtilisateurService;
    }
    @PostMapping("/login")
    public TokenObject login(@RequestBody Credentials credentials){
        String token=this.connectedUtilisateurService.genereTokenAuthentificationUtilisateur(credentials);
        //return new ResponseEntity<>(TokenObject.builder().token(jwtToken.getToken()).build(),headers, HttpStatus.OK);
        TokenObject tokenObject= TokenObject.builder().token(token).build();
        return tokenObject;
    }
    @GetMapping("/updateConnected")
    //@PreAuthorize("hasAnyAuthority('ADMIN')")
    public UtilisateurConnectedDTO updateConnected(){
       UtilisateurConnectedDTO userConnectedDTO= this.connectedUtilisateurService.getConnectedUtilisateur();
     return userConnectedDTO;
    }

}
