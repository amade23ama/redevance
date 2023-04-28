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
@Table(name = "SITE")
public class SiteEntity {
    public static final String ID_SEQUENCE_NAME = "SEQ_SITE";
    @Id
    @Column(name = "ID_SITE", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;
    @Column(name = "NOM")
    private  String nom;
    @Column(name = "LOCALITE")
    private  String localite;
    @Column(name ="DATECREATION")
    private Date dateCreation;
    @Column(name ="DATEMODIFICATION")
    private Date dateModification;
}
