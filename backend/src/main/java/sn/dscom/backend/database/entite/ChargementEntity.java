package sn.dscom.backend.database.entite;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CHARGEMENT")
public class ChargementEntity {

    public static final String ID_SEQUENCE_NAME = "SEQ_CHARGEMENT";

    @Id
    @Column(name = "ID_CHARGEMENT", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "DATEPASSAGE")
    private Date datePassage;

    @Column(name = "HEUREPASSAGE")
    private Date  heurePassage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SITE", nullable = false)
    private SiteEntity siteEntity ;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "DEPOT_CHARGEMENT",
            joinColumns = @JoinColumn(name = "ID_CHARGEMENT"),
            inverseJoinColumns = @JoinColumn(name = "ID_DEPOT"))
    private List<DepotEntity> depots;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SITE_EXPLOITATION", nullable = false)
    private ExploitationEntity exploitationEntity;

    @Column(name = "DESTINATION")
    private String destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUIT", nullable = false)
    private ProduitEntity produitEntity;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULE", nullable = false)
    private VehiculeEntity vehiculeEntity;
    @ManyToOne
    @JoinColumn(name = "ID_TRANSPORTEUR", nullable = false)
    private TransporteurEntity transporteurEntity;

    @Column(name = "POIDS")
    private Double poids;

    @Column(name = "POIDS_MAX")
    private Double poidsMax;

    @Column(name = "POIDS_SUBST")
    private Double poidsSubsitance;

    @Column(name = "VOLUME_SUBST")
    private Double volumeSubsitance;

    @Column(name = "VOLUME_MOYEN")
    private Double volumeMoyen;

    @Column(name = "ECART")
    private Double ecart;

    @Column(name = "DATECREATION")
    private Date dateCreation;

    @Column(name = "DATEMODIFICATION")
    private Date dateModification;


}
