package sn.dscom.backend.common.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ReportingDTO
 */
@Data
@Builder
public class CampagneDTO implements Serializable {

    /** quantite */
    private double quantite;

    /** libelle */
    private String libelle;
}
