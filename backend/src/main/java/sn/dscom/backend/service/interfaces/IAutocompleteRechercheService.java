package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.AutocompleteRecherche;

import java.util.List;

/**
 * IAutocompleteRechercheService
 * @author diome
 */
public interface IAutocompleteRechercheService {

    /**
     * getUtilisateurAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    List<AutocompleteRecherche> getUtilisateurAutocompleteRecherche(String capture);

    /**
     * getUtilisateurAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    List<AutocompleteRecherche> getProfilAutocompleteRecherche(String capture);

    /**
     * getProduitAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    List<AutocompleteRecherche> getProduitsAutocompleteRecherche(String capture);

    /**
     * getSitesPessageAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    List<AutocompleteRecherche> getSitesPessageAutocompleteRecherche(String capture);

    /**
     * getSitesExploitationAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    List<AutocompleteRecherche> getSitesExploitationAutocompleteRecherche(String capture);

    /**
     * getVoitureAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    List<AutocompleteRecherche> getVoitureAutocompleteRecherche(String capture);

    /**
     * getChargementAutocompleteRecherche
     * @param capture capture
     * @return la liste
     */
    List<AutocompleteRecherche> getChargementAutocompleteRecherche(String capture);
}
