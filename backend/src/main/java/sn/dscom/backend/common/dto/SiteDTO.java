package sn.dscom.backend.common.dto;
import jakarta.persistence.Column;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteDTO {
    //todo
    private Long id;
    private  String nom;
    private  String localite;
    private Date dateCreation;
    private Date dateModification;
}
