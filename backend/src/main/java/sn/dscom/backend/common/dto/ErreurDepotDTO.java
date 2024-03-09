package sn.dscom.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErreurDepotDTO implements Serializable {
    private Long id;
    private Long idDepot;
    private String  message;
}
