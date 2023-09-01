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

    /** campagnes */
    private List<CampagneBilanDTO> campagnes;

}
