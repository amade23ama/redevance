package sn.dscom.backend.controller;

import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.dscom.backend.common.dto.AutocompleteRecherche;
import sn.dscom.backend.service.interfaces.IAutocompleteRechercheService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/autocomplete")
public class AutocompleteRechercheController {

    /** Logger Factory */
    private static final Logger LOGGER = LoggerFactory.getLogger(AutocompleteRechercheController.class);

    /** autocomplete RechercheService */
    @Autowired
    private IAutocompleteRechercheService autocompleteRechercheService;

    /**
     * getProduitAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    @GetMapping("/produit/{capture}")
    public ResponseEntity<List<AutocompleteRecherche>> getProduitAutocompleteRecherche(@PathVariable String capture) {

        return ResponseEntity.ok(
                Try.of(() -> capture)
                        .mapTry(this.autocompleteRechercheService::getProduitsAutocompleteRecherche)
                        .onFailure(Throwable::getStackTrace)
                        .get()
        );
    }

    /**
     * getSitesPessageAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    @GetMapping("/sitesPessage/{capture}")
    public ResponseEntity<List<AutocompleteRecherche>> getSitesPessageAutocompleteRecherche(@PathVariable String capture) {

        return ResponseEntity.ok(
                Try.of(() -> capture)
                        .mapTry(this.autocompleteRechercheService::getSitesPessageAutocompleteRecherche)
                        .onFailure(Throwable::getStackTrace)
                        .get()
        );
    }

    /**
     * getSitesExploitationAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    @GetMapping("/sitesExploitation/{capture}")
    public ResponseEntity<List<AutocompleteRecherche>> getSitesExploitationAutocompleteRecherche(@PathVariable String capture) {

        return ResponseEntity.ok(
                Try.of(() -> capture)
                        .mapTry(this.autocompleteRechercheService::getSitesExploitationAutocompleteRecherche)
                        .onFailure(Throwable::getStackTrace)
                        .get()
        );
    }

    /**
     * getVoituresAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    @GetMapping("/voitures/{capture}")
    public ResponseEntity<List<AutocompleteRecherche>> getVoituresAutocompleteRecherche(@PathVariable String capture) {

        return ResponseEntity.ok(
                Try.of(() -> capture)
                        .mapTry(this.autocompleteRechercheService::getVoitureAutocompleteRecherche)
                        .onFailure(Throwable::getStackTrace)
                        .get()
        );
    }

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
