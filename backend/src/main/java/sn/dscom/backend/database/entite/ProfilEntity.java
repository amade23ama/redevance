package sn.dscom.backend.database.entite;

import jakarta.persistence.*;
//import jakarta.persistence.Table;
import lombok.*;
//import org.springframework.data.annotation.Id;
/*
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
*/

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROFIL")
public class ProfilEntity {
    @Id
    @Column(name ="DROIT")
    private String code;
    @Column(name="LIBELLE")
    private String libelle;
}
