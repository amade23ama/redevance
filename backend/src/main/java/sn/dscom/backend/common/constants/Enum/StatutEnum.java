package sn.dscom.backend.common.constants.Enum;


import lombok.Getter;

/**
 * Énumération de type Statut
 */
@Getter
public enum StatutEnum {

    ERREUR("ERREUR"),
    ENCOURS("ENCOURS"),
    SUCCES("SUCCES");

    /**
     * Code du Statut.
     */
    private final String code;

    /**
     * Constructeur
     * @param code code du Statut.
     */
    StatutEnum(final String code) {
        this.code = code;
    }
}
