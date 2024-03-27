package sn.dscom.backend.common.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
    //private TransporteurDTO transporteur;

    /** categorie du véhicule */
    private  CategorieDTO categorie;

    /** poids Vide */
    private  Double poidsVide;

    /** date Creation */
    private Date dateCreation;

    /** date Modification */
    private Date dateModification;
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VehiculeDTO other = (VehiculeDTO) obj;

        return Objects.equals(id, other.id) &&
                Objects.equals(immatriculation, other.immatriculation) &&
                Objects.equals(categorie.getType(), other.categorie.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, immatriculation,categorie.getType());
    }
}
