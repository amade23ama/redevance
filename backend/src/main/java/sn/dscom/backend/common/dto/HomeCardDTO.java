package sn.dscom.backend.common.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Home card -> tuile.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeCardDTO implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 66L;

    /** Type de la tuile. */
    private String typeTuile;

    /** Valeur. */
    private Integer valeur;

    /** Details d'une tuile. */
    private List<DetailDTO> details;

    /** Campagnes d'une tuile. */
    private List<CampagneDTOOLD> campagnes;
}