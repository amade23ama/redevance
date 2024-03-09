package sn.dscom.backend.service.converter;

import sn.dscom.backend.common.dto.ErreurDepotDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ErreurDepotEntity;

public class ErreurDepotConverter implements Transformer<ErreurDepotDTO, ErreurDepotEntity> {
    @Override
    public ErreurDepotDTO reverse(ErreurDepotEntity erreurDepotEntity) {
        if (erreurDepotEntity == null) {
            return null;
        }
        return ErreurDepotDTO.builder()
                .id(erreurDepotEntity.getId())
                .idDepot(erreurDepotEntity.getIdDepot())
                .message(erreurDepotEntity.getMessage())
                .build();
    }

    @Override
    public ErreurDepotEntity transform(ErreurDepotDTO erreurDepotDTO) {
        return ErreurDepotEntity.builder()
                .idDepot(erreurDepotDTO.getIdDepot())
                .message(erreurDepotDTO.getMessage())
                .id(erreurDepotDTO.getId())
                .build();
    }
}
