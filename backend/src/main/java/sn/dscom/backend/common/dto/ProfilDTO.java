package sn.dscom.backend.common.dto;
import jakarta.persistence.Column;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilDTO {
    private String code;
    private String libelle;
}
