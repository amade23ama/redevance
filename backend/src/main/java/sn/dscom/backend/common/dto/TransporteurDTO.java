package sn.dscom.backend.common.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import sn.dscom.backend.database.entite.VehiculeEntity;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransporteurDTO {
    //todo
    private Long id;
    private String type;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String adresse;
    private Date dateCreation;
    private Date dateModification;
    private List<VehiculeEntity> vehiculeEntityListes;
}
