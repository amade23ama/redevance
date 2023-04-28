package sn.dscom.backend.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.entite.UtilisateurEntity;

import javax.validation.Valid;

@Component
public class ChargementConverter {
    @Autowired
    ChargementConverter(){super();}
    public static ChargementDTO toChargementDTO(final @Valid ChargementEntity chargementEntity) {
        //todo
        return null;
    }
    public static ChargementEntity toChargementEntity(
            final ChargementDTO chargementDTO) {
        // todo
        final ChargementEntity chargementEntity = new ChargementEntity();
       // chargementEntity.setId(chargementDTO.getId());

        return chargementEntity;
    }
}
