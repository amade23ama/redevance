package sn.dscom.backend.database.entite;
import lombok.*;
import jakarta.persistence.*;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANSPORTEUR")
public class TransporteurEntity {
    public static final String ID_SEQUENCE_NAME = "SEQ_TRANSPORTEUR";
    @Id
    @Column(name = "ID_TRANSPORTEUR", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;
    @Column(name ="TYPE")
    private String type;
    @Column(name ="NOM_RAISON_SOCIALE")
    private String nom;
    @Column(name ="PRENOM")
    private String prenom;
    @Column(name ="TELEPHONE")
    private String telephone;
    @Size(max = 320)
    @Column(name = "EMAIL", length = 254)
    private String email;
    @Column(name ="ADRESSE")
    private String adresse;
    @Column(name ="DATECREATION")
    private Date dateCreation;
    @Column(name ="DATEMODIFICATION")
    private Date dateModification;

    // TODO: à voir avec l'équipe
    /*@OneToMany(mappedBy = "transporteurEntity")
    private List<VehiculeEntity> vehiculeEntityListes;

    public void setVehiculeEntityListes( List<VehiculeEntity> nouvelleListe) {
        if (!CollectionUtils.isEmpty(nouvelleListe)) {
            if (vehiculeEntityListes == null) {
                vehiculeEntityListes = new ArrayList<>();
            } else {
                vehiculeEntityListes.clear();
            }
            vehiculeEntityListes.addAll(nouvelleListe);
        }
    }
*/

}
