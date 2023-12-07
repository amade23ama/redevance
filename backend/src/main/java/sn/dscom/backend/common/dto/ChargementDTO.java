package sn.dscom.backend.common.dto;
import lombok.*;
import sn.dscom.backend.database.entite.DepotEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private Long idDepot;

    /** le site*/
    private SiteDTO site;

    /** le site*/
    private VehiculeDTO vehicule;

    /** le site d'exploitation */
    private ExploitationDTO exploitation;

    /**  d'produit */
    private ProduitDTO produit;
    /** le fichier à charger */
    private List<DepotDTO> depotDTOList ;
    /** le transporteur transporteur */
    private TransporteurDTO transporteur;
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChargementDTO other = (ChargementDTO) obj;

        // Compare all relevant fields for equality
        return Objects.equals(id, other.id) &&
                Objects.equals(site.getId(), other.site.getId()) &&
                Objects.equals(produit.getId(), other.produit.getId()) &&
                Objects.equals(vehicule.getId(), other.vehicule.getId()) &&
                Objects.equals(transporteur.getId(), other.transporteur.getId()) &&
                Objects.equals(destination, other.destination) &&
                Objects.equals(poids, other.poids) &&
                Objects.equals(poidsMax, other.poidsMax) &&
                Objects.equals(poidsSubst, other.poidsSubst) &&
                Objects.equals(volumeSubst, other.volumeSubst) &&
                Objects.equals(volumeMoyen, other.volumeMoyen) &&
                Objects.equals(ecart, other.ecart) ;
        /* &&
                Objects.equals(destination, other.destination) &&
                Objects.equals(datePesage, other.datePesage) &&
                Objects.equals(poids, other.poids) &&
                Objects.equals(poidsMax, other.poidsMax) &&
                Objects.equals(poidsSubst, other.poidsSubst) &&
                Objects.equals(volumeSubst, other.volumeSubst) &&
                Objects.equals(volumeMoyen, other.volumeMoyen) &&
                Objects.equals(ecart, other.ecart) &&
                Objects.equals(dateCreation, other.dateCreation) &&
                Objects.equals(dateModif, other.dateModif) &&
                Objects.equals(subtance, other.subtance) &&
                Objects.equals(plateforme, other.plateforme) &&
                Objects.equals(idDepot, other.idDepot) &&
                Objects.equals(site.getId(), other.site.getId()) &&
                Objects.equals(vehicule.getId(), other.vehicule.getId()) &&
                Objects.equals(exploitation.getId(), other.exploitation.getId()) &&
                Objects.equals(produit.getId(), other.produit.getId());*/
    }

    @Override
    public int hashCode() {
        // Use all relevant fields for hashing
        return Objects.hash(id, destination, poids, poidsMax, poidsSubst,
                volumeSubst, volumeMoyen, ecart, site.getId(), vehicule.getId(),transporteur.getId(), produit.getId()
                );
    }

}
