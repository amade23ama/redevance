package sn.dscom.backend.common.constants.Enum;

public enum ErreurEnum {
    ERR_FiLE_NOT_FOUND("aucun fichier"),
    ERR_CONTRAT_NOT_FOUND("error.contratNotFound"),
    ERR_NOT_FOUND("error.contratNotFound"),
    ERR_INATTENDUE("error.inattendue"),
    ERR_CONTRAT_DROIT_INSUFFISANT("erreur.contrat.droit.insuffisant"),
    ERR_DROIT_INSUFFISANT("erreur.droit.insuffisant");
    /**
     * Code de l'erreur
     */
    private final String code;

    /**
     * Type d'erreur
     * true pour une erreur de type formulaire (affichée dans le formulaire correspondant)
     * false (valeur par défaut) pour une erreur générale (affichée en haut de page)
     */
    private final boolean typeForm;

    /**
     * Constructeur
     * @param code code du droit
     */
    ErreurEnum(final String code) {
        this(code, false);
    }

    /**
     * Constructeur
     * @param code     code du droit
     * @param typeForm Type d'erreur : formulaire ou générale
     */
    ErreurEnum(final String code, final boolean typeForm) {
        this.code = code;
        this.typeForm = typeForm;
    }


}
