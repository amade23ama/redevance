package sn.dscom.backend.database.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

//import javax.persistence.*;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEPOT")
public class DepotEntity {
    public static final String ID_SEQUENCE_NAME = "SEQ_DEPOT";
    @Id
    @Column(name = "ID_DEPOT", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;
    @Column(name = "NOM_DEPOT")
    private String nom;
    @Column(name = "DATEHEUREDEBUT")
    private Date dateHeureDepot;
    @Column(name = "DATEHEUREFIN")
    private Date dateHeureFinDepot;
    @Column(name = "NOM_FICHIER")
    private String nomFichier;
    @ManyToOne
    @JoinColumn(name = "ID_UTILISATEUR", nullable = false)
    private UtilisateurEntity deposeur;
    @Column(name = "NB_CHARGEMENT_DEPOT")
    private Integer nbChargementDeposes;
    @Column(name = "NB_CHARGEMENT_REDEBOT")
    private Integer nbChargementReDeposes;
    @Column(name = "NB_ERREUR")
    private Integer nbChargementErreur;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "DEPOT_CHARGEMENT",
            joinColumns = @JoinColumn(name = "ID_DEPOT"),
            inverseJoinColumns = @JoinColumn(name = "ID_CHARGEMENT"))
    private List<ChargementEntity> chargementEntityList;

}
