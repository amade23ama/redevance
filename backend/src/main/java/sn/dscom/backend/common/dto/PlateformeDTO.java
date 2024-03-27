package sn.dscom.backend.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Plateforme de chargement
 * @author diome
 */
@Data
@Builder
public class PlateformeDTO {
    private Long id;

    /** emplacement */
    public String emplacement;

    /** la date Creation*/
    public Date dateCreation;

    /** date Modif*/
    public Date dateModif;
}
