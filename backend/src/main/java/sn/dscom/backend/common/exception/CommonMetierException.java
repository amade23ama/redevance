package sn.dscom.backend.common.exception;

import lombok.Getter;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;

import java.util.ArrayList;
import java.util.List;
@Getter
public class CommonMetierException extends RuntimeException {

    /**
     * SerialVersionUID.
     */
    private static final long serialVersionUID = -5408922422368586427L;

    /**
     * Statut HTTP à renvoyer par défaut : BAD_REQUEST
     */
    private Integer httpStatus = 400;

    /**
     * Erreur
     */
    private final ErreurEnum erreur;

    /**
     * Liste des paramètres du code
     */
    private final List<String> substitutes = new ArrayList<>();

    /**
     * Constructeur contenant uniquement un code (httpStatus par défaut BAD_REQUEST)
     *
     * @param erreur erreur
     */
    public CommonMetierException(final ErreurEnum erreur) {
        super();
        this.erreur = erreur;
    }

    /**
     * Constructeur avec un code et ses parametres (httpStatus par défaut BAD_REQUEST)
     *
     * @param erreur erreur
     * @param substi paramètres contenus dans le code d'erreur
     */
    public CommonMetierException(final ErreurEnum erreur, final List<String> substi) {
        super();
        this.erreur = erreur;
        substitutes.addAll(substi);
    }

    /**
     * Constructeur avec un statut HTTP et un code
     *
     * @param httpStatus Statut HTTP
     * @param erreur     erreur
     */
    public CommonMetierException(final Integer httpStatus, final ErreurEnum erreur) {
        super();
        this.httpStatus = httpStatus;
        this.erreur = erreur;
    }

    /**
     * Constructeur avec tous les arguments
     *
     * @param httpStatus Statut HTTP
     * @param erreur     erreur
     * @param substi     paramètres contenus dans le code d'erreur
     */
    public CommonMetierException(final Integer httpStatus, final ErreurEnum erreur, final List<String> substi) {
        super();
        this.httpStatus = httpStatus;
        this.erreur = erreur;
        substitutes.addAll(substi);
    }
}
