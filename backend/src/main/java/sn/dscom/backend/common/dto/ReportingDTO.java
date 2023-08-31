package sn.dscom.backend.common.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ReportingDTO
 */
@Data
@Builder
public class ReportingDTO implements Serializable {

    /** type */
    private String type;

    /** libelle */
    private String libelle;

    /** annee */
    private String annee;

    /** data */
    private Double data;
}