package sn.dscom.backend.database.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.util.CollectionUtils;
//import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UTILISATEUR")
public class UtilisateurEntity {

    public static final String ID_SEQUENCE_NAME = "SEQ_UTILISATEUR";
    @Id
    @Column(name = "ID_UTILISATEUR", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;
    @Column(name ="PRENOM")
    private String prenom;
    @Column(name ="NOM")
    private String nom;
    @Size(max = 320)
    @Column(name = "EMAIL", length = 254)
    private String email;
    @Column(name ="LOGIN")
    private String login;
    @Column(name ="PASSWORD")
    private String password;
    @Column(name ="TELEPHONE")
    private String telephone;
    @Column(name ="DATECREATION")
    private Date dateCreation;
    @Column(name ="DATEMODIFICATION")
    private Date dateModification;
    @Column(name ="ACTIF")
    private boolean active;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UTILISATEUR_PROFIL",
            joinColumns = @JoinColumn(name = "ID_UTILISATEUR"),
            inverseJoinColumns = @JoinColumn(name = "DROIT"))
    private List<ProfilEntity> profils;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch =FetchType.LAZY,mappedBy = "deposeur")
    @JsonManagedReference
    private List<DepotEntity> depotEntityList;

    public void setProfils( List<ProfilEntity> nouvelleListe) {
        if (!CollectionUtils.isEmpty(nouvelleListe)) {
            if (profils == null) {
                profils = new ArrayList<>();
            } else {
                profils.clear();
            }
            profils.addAll(nouvelleListe);
        }
    }
    public void setDepotEntityList( List<DepotEntity> nouvelleListe) {
        if (!CollectionUtils.isEmpty(nouvelleListe)) {
            if (depotEntityList == null) {
                depotEntityList = new ArrayList<>();
            } else {
                depotEntityList.clear();
            }
            depotEntityList.addAll(nouvelleListe);
        }
    }
}
