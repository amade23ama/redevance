package sn.dscom.backend.common.util;

import sn.dscom.backend.common.dto.UtilisateurDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(chaine);
        String chainePropre = matcher.replaceAll("");
        return chainePropre;
    }
    public static String supprimerCaracteresSpeciauxSaufEspace(String chaine) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s]");
        Matcher matcher = pattern.matcher(chaine);
        String chainePropre = matcher.replaceAll("");
        return chainePropre;
    }
    public static String supprimerCaracteresSpeciauxSaufEspaceTiret(String chaine) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s-]");
        Matcher matcher = pattern.matcher(chaine);
        String chainePropre = matcher.replaceAll("");
        return chainePropre;
    }


    public static String getDate(Date dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstants.DATE_COURT);
        return dateFormat.format(dateTime);
    }
    public static String getTime(Date dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstants.DATE_HEURE);
        return dateFormat.format(dateTime);
    }
}
