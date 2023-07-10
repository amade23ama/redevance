package sn.dscom.backend.common.dto;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Objet pivot: ChargementDTO
 */
@Data
@Builder
public class ChargementDTO {

    /** la destination du chargement*/
    private String destination;

    /** la date du chargement*/
    private Date datePesage;

    /** le poid du chargement*/
    private String poids;

    /** le poid max du chargement*/
    private String poidsMax;

    /** le poid Subst du chargement*/
    private String poidsSubst;

    /** le volume du chargement*/
    private String volumeSubst;

    /** le volume moyeb du chargement*/
    private String volumeMoyen;

    /** ecart */
    private String ecart;

    /** la date Creation*/
    private Date dateCreation;

    /** date Modif*/
    private Date dateModif;

    /** date Modif*/
    private SubtanceDTO subtance;

    /** la plateforme*/
    private PlateformeDTO plateforme;

    /** le depot*/
    private DepotDTO depot;

    /** le site*/
    private SiteDTO site;

    /** le site*/
    private List<VehiculeDTO> vehicules;
}
