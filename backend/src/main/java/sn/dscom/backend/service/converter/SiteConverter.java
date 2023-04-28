package sn.dscom.backend.service.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.dscom.backend.common.dto.ProfilDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.database.entite.ProfilEntity;
import sn.dscom.backend.database.entite.SiteEntity;

import javax.validation.Valid;
import java.util.Date;

@Component
public class SiteConverter {
    @Autowired
    SiteConverter(){super();}

    public static SiteDTO toSiteDTO(@Valid SiteEntity siteEntity) {
        //todo
        if (siteEntity == null) {
            return null;
        }
        return SiteDTO.builder()
                .id(siteEntity.getId())
                .nom(siteEntity.getNom())
                .localite(siteEntity.getLocalite())
                .dateCreation(siteEntity.getId() == null ? new Date()  :siteEntity.getDateCreation())
                .dateModification(siteEntity == null ? null :siteEntity.getDateModification())
                .build();
    }
    public static SiteEntity toSiteEntity( SiteDTO siteDTO) {
        // todo
        if (siteDTO == null) {
            return null;
        }

        final SiteEntity siteEntity = SiteEntity.builder()
                .id(siteDTO.getId())
                .nom(siteDTO.getNom())
                .localite(siteDTO.getLocalite())
                .dateCreation(siteDTO.getDateCreation() == null ? new Date() :siteDTO.getDateCreation())
                .dateModification(siteDTO == null ? null :siteDTO.getDateModification())
                .build();
        return siteEntity;
    }
}
