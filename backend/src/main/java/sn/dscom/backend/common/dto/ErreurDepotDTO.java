package sn.dscom.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErreurDepotDTO implements Serializable {
    private Long id;
    private Long idDepot;
    private String  message;
    private Long nombreErreur;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ErreurDepotDTO other = (ErreurDepotDTO) obj;

        return Objects.equals(id, other.id) &&
                Objects.equals(idDepot, other.getIdDepot()) &&
                Objects.equals(message, other.getMessage()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idDepot, message);
    }
}
