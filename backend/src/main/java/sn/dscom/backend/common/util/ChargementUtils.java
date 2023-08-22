package sn.dscom.backend.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe utilitaire
 */
public abstract class ChargementUtils {

    // 25 %
    public static final double POIDS_25 = 0.25;

    /**
     * get Nom Or RaisonSociale
     * @param theParam theParam
     * @return Nom Or RaisonSociale
     */
    public static String getNomOrRaisonSociale(final String theParam){
        return StringUtils.substringBefore(theParam,"/").toUpperCase();
    }

    /**
     * get Telephone
     * @param theParam theParam
     * @return telephone
     */
    public static String getTelephone(final String theParam){
        return StringUtils.substringAfter(theParam,"/");
    }

    /**
     * Le poids estimé est la difference entre poids mesure et le poids de la voiture vide
     *
     * @param poidsMesure le poids mesuré par la plateforme
     * @param poidsMax le poids maximum par la plateforme
     * @return le poids Estimé
     */
    public static Double getPoidsEstime(final Double poidsMesure, final Double poidsMax, final Double poidsVehicul){

            return precisionDouble(poidsMesure - POIDS_25 * poidsMax);
    }

    /**
     * L'ecart est la difference entre le volume estimé et le volume de la classe
     *
     * @param volumeEstime le volume Estime
     * @param volumeClasse le volume Classe
     * @return l'ecart
     */
    public static Double getEcart(final Double volumeEstime, final Double volumeClasse){
        return precisionDouble(volumeEstime - volumeClasse);
    }

    /**
     *  C'est l'ecart divisé par 2
     *
     * @param volumeEstime volume Estime
     * @param volumeClasse volume de la Classe
     * @return Volume Moyen
     */
    public static Double getVolumeMoyen(final Double volumeEstime, final Double volumeClasse){
        return precisionDouble((volumeEstime + volumeClasse)/2);
    }

    /**
     * C'est le poids estimé sur la dentisité
     * @param poidsEstime poids Estime
     * @param densiteProduit densite Produit kg/m3
     * @return Volume Estime
     */
    public static Double getVolumeEstime(final Double poidsEstime, final Double densiteProduit){
        return precisionDouble(poidsEstime/densiteProduit);
    }

    /**
     * precisionDouble
     * @param value value
     * @return Double
     */
    private static Double precisionDouble(Double value) throws ArithmeticException {
        try {
            return BigDecimal.valueOf(value).setScale(3, RoundingMode.UP).doubleValue();
        } catch (ArithmeticException exception) {
            throw new ArithmeticException();
        }
    }

    /**
     * getDateHeureChargement
     * @param date date
     * @param heure  heure
     * @return Date
     * @throws ParseException ParseException
     */
    public static Date getDateHeureChargement(String date, String heure){
        String dateHeure = date.concat(" ").concat(heure);
        SimpleDateFormat parser = new SimpleDateFormat("d/MM/yyyy HH:mm:ss");
        try {
            return  parser.parse(dateHeure);
        }catch (ParseException exception){
            exception.getStackTrace();
        }
        return new Date();
    }
}
