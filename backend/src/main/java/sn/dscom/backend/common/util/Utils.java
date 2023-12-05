package sn.dscom.backend.common.util;

import sn.dscom.backend.common.dto.UtilisateurDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String generateLogin(UtilisateurDTO utilisateurDTO) {
        String loginPrenom = utilisateurDTO.getPrenom().trim().toLowerCase();
        String loginNom = utilisateurDTO.getNom().trim().toLowerCase();
        String login = loginNom+loginPrenom.substring(0,2);
        return login;
    }
    public static String supprimerCaracteresSpeciaux(String chaine) {
        // Utiliser une expression régulière pour ne garder que les lettres et les chiffres
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(chaine);
        String chainePropre = matcher.replaceAll("");
        return chainePropre;
    }
    public static String supprimerCaracteresSpeciauxSaufEspace(String chaine) {
        // Utiliser une expression régulière pour ne garder que les lettres et les chiffres
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s]");
        Matcher matcher = pattern.matcher(chaine);
        String chainePropre = matcher.replaceAll("");
        return chainePropre;
    }
    public static String supprimerCaracteresSpeciauxSaufEspaceTiret(String chaine) {
        // Utiliser une expression régulière pour ne garder que les lettres, les chiffres, les espaces et les tirets
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s-]");
        Matcher matcher = pattern.matcher(chaine);
        String chainePropre = matcher.replaceAll("");
        return chainePropre;
    }
}
