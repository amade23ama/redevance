package sn.dscom.backend.service.converter;

import sn.dscom.backend.common.dto.ReferenceAnneeDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ReferenceAnneeEntity;
import sn.dscom.backend.database.entite.SiteEntity;

import java.util.Date;

public class ReferenceAnneeConverter implements Transformer<ReferenceAnneeDTO, ReferenceAnneeEntity> {
    @Override
    public ReferenceAnneeDTO reverse(ReferenceAnneeEntity referenceAnneeEntity) {
        if (referenceAnneeEntity == null) {
            return null;
        }
        return ReferenceAnneeDTO.builder()
                .id(referenceAnneeEntity.getId())
                .annee(referenceAnneeEntity.getAnneePesage())
                .build();
    }

    @Override
    public ReferenceAnneeEntity transform(ReferenceAnneeDTO referenceAnneeDTO) {
        //on retourne null si l'objet d'entré est null
        if (referenceAnneeDTO == null) {
            return null;
        }

        final ReferenceAnneeEntity referenceAnneeEntity = ReferenceAnneeEntity .builder()
                .id(referenceAnneeDTO.getId())
                .anneePesage(referenceAnneeDTO.getAnnee())
                .build();
        return referenceAnneeEntity;
    }
}
