package sn.dscom.backend.common.constants.Enum;

import lombok.Getter;

/**
 * Énumération des droits.
 */
@Getter
public enum DroitEnum {

    /**
     * Droit pour pouvoir charger les données de configuration.
     */
    CONFIGURATION("CONFIGURATION"),
    ADMIN("ADMIN"),
    CONSULT("CONSULT"),
    UTILISATEUR("UTILISATEUR"),
    TUILES("TUILES");
    /**
     * Code de la configuration
     */
    private final String code;

    /**
     * Constructeur
     *
     * @param code code du droit
     */
    DroitEnum(final String code) {
        this.code = code;
    }
}
