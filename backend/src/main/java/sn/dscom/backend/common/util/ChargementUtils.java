package sn.dscom.backend.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe utilitaire
 */
public abstract class ChargementUtils {

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
}
