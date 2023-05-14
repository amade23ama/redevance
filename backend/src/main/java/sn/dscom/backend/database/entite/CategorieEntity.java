package sn.dscom.backend.database.entite;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORIE")
public class CategorieEntity {
    public static final String ID_SEQUENCE_NAME = "SEQ_CATEGORIE";
    @Id
    @Column(name = "ID_CATEGORIE", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "VOLUME")
    private  Double volume;
    @Column(name ="DATECREATION")
    private Date dateCreation;
    @Column(name ="DATEMODIFICATION")
    private Date dateModification;

}
