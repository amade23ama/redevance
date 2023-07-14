package sn.dscom.backend.common.dto;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * ProduitDTO
 */
@Data
@Builder
public class ProduitDTO implements Serializable {

    /** identifiant */
    private Long id;

    /** identifiant */
    private  String nomSRC;

    /** identifiant */
    private  String nomNORM;

    /** identifiant */
    private  Double densiteGRM;

    /** identifiant */
    private  Double densiteKGM;

    /** identifiant */
    private Date dateCreation;

    /** identifiant */
    private Date dateModification;
}
