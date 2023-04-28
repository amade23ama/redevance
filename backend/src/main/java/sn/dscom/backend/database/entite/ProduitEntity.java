package sn.dscom.backend.database.entite;


import lombok.*;

//import javax.persistence.*;
import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUIT")
public class ProduitEntity {

    public static final String ID_SEQUENCE_NAME = "SEQ_PRODUIT";
    @Id
    @Column(name = "ID_PRODUIT", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;
    @Column(name = "NOM_SRC")
    private  String nomSRC;
    @Column(name = "NOM_NORM")
    private  String nomNORM;
    @Column(name = "DENSITE_GRM")
    private  Double densiteGRM;
    @Column(name = "DENSITE_KGM")
    private  Double densiteKGM;
    @Column(name ="DATECREATION")
    private Date dateCreation;
    @Column(name ="DATEMODIFICATION")
    private Date dateModification;
}
