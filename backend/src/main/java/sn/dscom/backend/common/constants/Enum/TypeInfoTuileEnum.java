package sn.dscom.backend.common.constants.Enum;

import lombok.Getter;

/**
 * Énumération de type info tuile.
 */
@Getter
public enum TypeInfoTuileEnum {
    DEPOT("DEPOT"),
    SITE("DEPOT"),
    /** En cours. */
    EN_COURS("EN.COURS");

    /**
     * Code de l'info tuile.
     */
    private final String code;

    /**
     * Constructeur
     * @param code code de l'info tuile.
     */
    TypeInfoTuileEnum(final String code) {
        this.code = code;
    }
}
