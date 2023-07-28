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

    /** l'id */
    private Long id;

    /** la destination du chargement*/
    private String destination;

    /** la date du chargement*/
    private Date datePesage;

    /** le poid du chargement*/
    private Double poids;

    /** le poid max du chargement*/
    private Double poidsMax;

    /** le poid Subst du chargement*/
    private Double poidsSubst;

    /** le volume du chargement*/
    private Double volumeSubst;

    /** le volume moyeb du chargement*/
    private Double volumeMoyen;

    /** ecart */
    private Double ecart;

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
    private VehiculeDTO vehicule;

    /** le site d'exploitation */
    private ExploitationDTO exploitation;

    /**  d'produit */
    private ProduitDTO produit;
}
