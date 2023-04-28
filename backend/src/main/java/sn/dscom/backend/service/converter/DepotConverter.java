package sn.dscom.backend.service.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.entite.DepotEntity;

import javax.validation.Valid;

@Component
public class DepotConverter {
    @Autowired
    DepotConverter(){super();}
    public static DepotDTO toDepotDTO(@Valid DepotEntity depotEntity) {
        //todo
        return null;
    }
    public static DepotEntity toDepotEntity(DepotDTO depotDTO) {
        // todo
        final DepotEntity depotEntity = new DepotEntity();
        // depotEntityEntity.setId(depotEntityDTO.getId());

        return depotEntity;
    }
}
