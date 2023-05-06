package sn.dscom.backend.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Campagne d'une tuile (home card).
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampagneDTO implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 64L;

    /** Année. */
    private Integer annee;

    /** Quantité. */
    private Integer quantite;
}
