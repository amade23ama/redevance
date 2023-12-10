package sn.dscom.backend.database.entite;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "REFERENCE_ANNEE")
public class ReferenceAnneeEntity {
    public static final String ID_SEQUENCE_NAME = "SEQ_REFERENCE_ANNEE";

    @Id
    @Column(name = "ID_REFERENCE_ANNEE", unique = true, nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQUENCE_NAME)
    @SequenceGenerator(name = ID_SEQUENCE_NAME, sequenceName = ID_SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "DATE_PESAGE",length = 4,columnDefinition = "bigint")
    @NotNull
    @Digits(integer = 4, fraction = 0, message = "datePesage  4 digits")
    private Long anneePesage;
}
