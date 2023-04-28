package sn.dscom.backend.database.entite;

import lombok.*;

//import javax.persistence.*;
import jakarta.persistence.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "ERREUR_DEPOT")
public class ErreurDepot {
    public static final String ID_SEQUENCE_NAME = "SEQ_ERREUR_DEPOT";
    @Id
    @Column(name = "ID_ERREUR_DEPOT", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;
    @Column(name = "messages")
   private String  message;

}
