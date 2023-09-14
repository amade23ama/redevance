package sn.dscom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.dscom.backend.common.dto.AutocompleteRecherche;
import sn.dscom.backend.service.AutocompleteRechercheService;
import sn.dscom.backend.service.interfaces.ICategorieService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/autocomplete")
public class AutocompleteRechercheController {
    @Autowired
    private AutocompleteRechercheService autocompleteRechercheService;
    @GetMapping("/utilisateur/{capture}")
    public List<AutocompleteRecherche> getUtilisateurAutocompleteRecherche(@PathVariable String capture) {

        final List<AutocompleteRecherche> result = new ArrayList<>();
        try {
            List<AutocompleteRecherche>  profils=   this.autocompleteRechercheService.getProfilAutocompleteRecherche(capture);
            List<AutocompleteRecherche>  utilisateur=    this.autocompleteRechercheService.getUtilisateurAutocompleteRecherche(capture);
            if(!profils.isEmpty()){
                result.addAll(profils);
            }

            if(!utilisateur.isEmpty()){
                result.addAll(utilisateur);
            }
        }catch (Exception e){

        }


        return result;
    }
}
