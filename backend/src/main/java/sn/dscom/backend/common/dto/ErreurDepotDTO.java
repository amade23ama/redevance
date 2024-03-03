package sn.dscom.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.dscom.backend.database.entite.DepotEntity;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErreurDepotDTO implements Serializable {
    private Long id;
    private DepotEntity depot;
    private String  message;
}
