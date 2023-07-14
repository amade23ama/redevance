package sn.dscom.backend.database.entite;


import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

/**
 * @apiNote ProduitEntity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUIT")
public class ProduitEntity {

    /** identifiant */
    public static final String ID_SEQUENCE_NAME = "SEQ_PRODUIT";
    @Id
    @Column(name = "ID_PRODUIT", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    /** nom SRC */
    @Column(name = "NOM_SRC")
    private  String nomSRC;

    /** nom NORM */
    @Column(name = "NOM_NORM")
    private  String nomNORM;

    /** densite GRM */
    @Column(name = "DENSITE_GRM")
    private  Double densiteGRM;

    /** densite KGM */
    @Column(name = "DENSITE_KGM")
    private  Double densiteKGM;

    /** date Creation */
    @Column(name ="DATECREATION")
    private Date dateCreation;

    /** date Modification */
    @Column(name ="DATEMODIFICATION")
    private Date dateModification;
}
