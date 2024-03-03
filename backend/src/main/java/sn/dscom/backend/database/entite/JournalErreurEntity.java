package sn.dscom.backend.database.entite;

import jakarta.persistence.*;

import java.util.List;

public class JournalErreurEntity {
    @ManyToOne
    @JoinColumn(name = "ID_DEPOT", nullable = false)


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "DEPOT_CHARGEMENT",
            joinColumns = @JoinColumn(name = "ID_CHARGEMENT"),
            inverseJoinColumns = @JoinColumn(name = "ID_DEPOT"))
    private List<DepotEntity> depots;
}
