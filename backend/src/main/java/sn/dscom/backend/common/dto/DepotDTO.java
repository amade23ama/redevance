package sn.dscom.backend.common.dto;

import jakarta.persistence.Column;
import lombok.*;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.database.entite.UtilisateurEntity;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * DepotDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepotDTO implements Serializable {

    /** id */
    private Long id;

    /** nom */
    private String nom;

    /** date Heure Depot */
    private Date dateHeureDepot;

    /** date Heure Fin Depot */
    private Date dateHeureFinDepot;

    /** nom Fichier */
    private String nomFichier;

    /** statut */
    private String statut;

    /** deposeur */
    private UtilisateurDTO deposeur;

    /** nb Chargement Deposes */
    private Integer nbChargementDeposes;

    /** nb Chargement ReDeposes */
    private Integer nbChargementReDeposes;

    /** nb Chargement Erreur */
    private Integer nbChargementErreur;

    /** le fichier à charger */
    private File file;

    /** le fichier à charger */
    private List<ChargementDTO> chargementDTOList;

    /** le site*/
    private SiteDTO site;
}
