package sn.dscom.backend.common.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * VehiculeDTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculeDTO implements Serializable {

    /** identifiant technique */
    private Long id;

    /** immatriculation du véhicule */
    private  String immatriculation;

    /** le transporteur transporteur */
    private TransporteurDTO transporteur;

    /** categorie du véhicule */
    private  CategorieDTO categorie;

    /** poids Vide */
    private  Double poidsVide;

    /** date Creation */
    private Date dateCreation;

    /** date Modification */
    private Date dateModification;

}
