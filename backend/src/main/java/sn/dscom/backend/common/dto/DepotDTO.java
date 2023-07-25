package sn.dscom.backend.common.dto;

import lombok.*;
import sn.dscom.backend.database.entite.UtilisateurEntity;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

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

    /** deposeur */
    private UtilisateurEntity deposeur;

    /** nb Chargement Deposes */
    private Integer nbChargementDeposes;

    /** nb Chargement ReDeposes */
    private Integer nbChargementReDeposes;

    /** nb Chargement Erreur */
    private Integer nbChargementErreur;

    /** le fichier à charger */
    private File file;

}
