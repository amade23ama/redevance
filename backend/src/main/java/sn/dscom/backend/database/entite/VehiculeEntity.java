package sn.dscom.backend.database.entite;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

@Data
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
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    /*@ManyToOne
    @JoinColumn(name = "ID_TRANSPORTEUR", nullable = false)
    private TransporteurEntity transporteurEntity;
    */
    //@Column(name = "NOM")
    //private  String nom;
    //@Column(name = "VOLUME")
    //private  Double volume;
    @Column(name = "POIDS_VIDE")
    private Double poidsVide;
    @Column(name ="DATECREATION")
    private Date dateCreation;
    @Column(name ="DATEMODIFICATION")
    private Date dateModification;
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIE", nullable = false)
    private CategorieEntity categorieEntity;

}
