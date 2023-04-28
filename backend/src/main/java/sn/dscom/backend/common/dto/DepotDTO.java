package sn.dscom.backend.common.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.entite.UtilisateurEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepotDTO {
    //todo
    private Long id;
    private String nom;
    private Date dateHeureDepot;
    private Date dateHeureFinDepot;
    private String nomFichier;
    private UtilisateurEntity deposeur;
    private Integer nbChargementDeposes;
    private Integer nbChargementReDeposes;
    private Integer nbChargementErreur;

}
