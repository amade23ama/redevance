package sn.dscom.backend.service;

import sn.dscom.backend.common.dto.Credentials;
import sn.dscom.backend.common.dto.UtilisateurConnectedDTO;
import sn.dscom.backend.database.entite.UtilisateurEntity;

public interface ConnectedUtilisateurService {


    /**
     * Renvoi l'utilisateur connecté.
     * @return utilisateur connecté
     */
    UtilisateurConnectedDTO getConnectedUtilisateur();

    /**
     * Renvoi l'id de l'utilisateur connecté.
     * @return id de l'utilisateur connecté
     */
    String getConnectedUserId();

    /**
     * Renvoi l'id de l'utilisateur  connecté.
     * @return id de l'utilisateur  connecté
     */
    Long getConnectedUtilisateurId();

    /**
     * Permet de savoir si l'utilisateur connecté détient le droit fourni.
     *
     * @param droit Droit à tester
     * @return true si l'utilisateur connecté détient le droit fourni
     */
    //boolean detientDroit(DroitEnum droit);

    /**
     * Renvoi l'utilisateur détail connecté.
     * @return utilisateur détail connecté
     */
    UtilisateurEntity chargerUtilisateur();
    String  genereTokenAuthentificationUtilisateur(Credentials credentials);

}
