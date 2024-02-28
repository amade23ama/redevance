package sn.dscom.backend.database.entite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TYPE_ERREUR")
public class TypeErreurEntity {
    @Id
    @Column(name ="CODE")
    private String code;
    @Column(name="LIBELLE")
    private String libelle;
    @Column(name="GRAVITE")
    private String gravite;
}
