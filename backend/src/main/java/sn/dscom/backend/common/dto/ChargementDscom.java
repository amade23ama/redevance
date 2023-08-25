package sn.dscom.backend.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * class de recherche
 * @author diome
 */
@Data
@Builder
public class ChargementDscom {

    /** Origine du chargement */
    private String origine;

    /** site De Pesage */
    private String siteDePesage;

    /** Destination du chargement */
    private String destination;

    /** Date de pesage */
    private Date datePesage;
}
