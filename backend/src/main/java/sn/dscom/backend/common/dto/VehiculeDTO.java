package sn.dscom.backend.common.dto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import sn.dscom.backend.database.entite.TransporteurEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculeDTO {
    //todo
    private Long id;
    private  String immatriculation;
    private TransporteurDTO transporteur;
    private  String nom;
    private  Double volume;
    private Date dateCreation;
    private Date dateModification;

}
