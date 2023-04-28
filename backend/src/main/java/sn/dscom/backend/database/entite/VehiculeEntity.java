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
@Table(name = "VEHICULE")
public class VehiculeEntity {
    public static final String ID_SEQUENCE_NAME = "SEQ_VEHICULE";
    @Id
    @Column(name = "ID_VEHICULE", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;
    @Column(name = "IMMATRICULATION")
    private  String immatriculation;
    /**
     * Id du Transporteur.
     */
    @ManyToOne
    @JoinColumn(name = "ID_TRANSPORTEUR", nullable = false)
    private TransporteurEntity transporteurEntity;
    @Column(name = "NOM")
    private  String nom;
    @Column(name = "VOLUME")
    private  Double volume;
    @Column(name ="DATECREATION")
    private Date dateCreation;
    @Column(name ="DATEMODIFICATION")
    private Date dateModification;

}
