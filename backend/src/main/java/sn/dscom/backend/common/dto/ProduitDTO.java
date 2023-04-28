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
public class ProduitDTO {
    //todo
    private Long id;
    private  String nomSRC;
    private  String nomNORM;
    private  Double densiteGRM;
    private  Double densiteKGM;
    private Date dateCreation;
    private Date dateModification;
}
