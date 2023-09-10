package sn.dscom.backend.common.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * BilanDTO
 */
@Data
@Builder
public class BilanDTO implements Serializable {

    /** annee */
    private int annee;

    /** description */
    private String description;

    /** Type de la tuile. */
    private String typeTuile;//"PRODUIT"/"REGION"/"ANNUEL"/"CHARGEMENT"/

    /** campagnes */
    private List<CampagneDTO> campagnes;

}
