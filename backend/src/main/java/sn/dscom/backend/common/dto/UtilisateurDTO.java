package sn.dscom.backend.common.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.database.entite.ProfilEntity;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UtilisateurDTO {
    //todo
    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private String login;
    private String password;
    private String telephone;
    private Date dateCreation;
    private Date dateModification;
    private boolean active;
    private List<ProfilDTO> profils;
    private List<DepotDTO> depotEntityListDTO;
}
