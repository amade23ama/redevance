package sn.dscom.backend.common.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * TransporteurDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransporteurDTO implements Serializable {
    /** id */
    private Long id;

    /** type */
    private String type;

    /** nom */
    private String nom;

    /** prenom */
    private String prenom;

    /** telephone */
    private String telephone;

    /** email */
    private String email;

    /** adresse */
    private String adresse;

    /** dateCreation */
    private Date dateCreation;

    /** dateModification */
    private Date dateModification;

    //TODO: ici on devrait retourner que la ref ou id des véhicules
    private List<VehiculeDTO> vehiculeListes;
}
