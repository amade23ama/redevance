package sn.dscom.backend.common.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorieDTO {
    private Long id;
    private String type;
    private  Double volume;
    private Date dateCreation;
    private Date dateModification;
}
