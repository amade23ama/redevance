package sn.dscom.backend.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.ChargementEntity;

import javax.validation.Valid;

@Component
public class CategorieConverter {
    @Autowired
    CategorieConverter(){
        super();
    };

    public static CategorieDTO toCategorieDTO(final @Valid CategorieEntity categorieEntity) {
        //todo
        return null;
    }
    public static CategorieEntity toCategorieEntity(final CategorieDTO categorieDTO) {
        // todo
        final CategorieEntity categorieEntity = new CategorieEntity();
        // chargementEntity.setId(chargementDTO.getId());

        return categorieEntity;
    }
}
