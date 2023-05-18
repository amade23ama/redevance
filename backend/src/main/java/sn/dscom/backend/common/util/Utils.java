package sn.dscom.backend.common.util;

import sn.dscom.backend.common.dto.UtilisateurDTO;

public class Utils {
    public static String generateLogin(UtilisateurDTO utilisateurDTO) {
        String loginPrenom = utilisateurDTO.getPrenom().trim().toLowerCase();
        String loginNom = utilisateurDTO.getNom().trim().toLowerCase();
        String login = loginNom+loginPrenom.substring(0,2);
        return login;
    }
}
