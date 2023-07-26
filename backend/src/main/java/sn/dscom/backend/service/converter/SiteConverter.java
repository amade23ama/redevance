package sn.dscom.backend.service.converter;

import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.SiteEntity;

/**
 * Site Converter
 */
public class SiteConverter implements Transformer<SiteDTO, SiteEntity> {

    /**
     * transformation de {@link SiteEntity} en {@link SiteDTO}
     *
     * @param siteEntity l'objet à transformer en {@link SiteDTO}
     * @return l'objet {@link SiteDTO}
     */
    @Override
    public SiteDTO reverse(SiteEntity siteEntity) {

        //on retourne null si l'objet d'entré est null
        if (siteEntity == null) {
            return null;
        }
        return SiteDTO.builder()
                .id(siteEntity.getId())
                .nom(siteEntity.getNom())
                .localite(siteEntity.getLocalite())
                .dateCreation(siteEntity.getDateCreation())
                .dateModification(siteEntity.getDateModification())
                .build();
    }

    /**
     * transformation de {@link SiteDTO} en {@link SiteEntity}
     *
     * @param siteDTO l'objet à transformer en {@link SiteEntity}
     * @return l'objet {@link SiteEntity}
     */
    @Override
    public SiteEntity transform(SiteDTO siteDTO) {

        //on retourne null si l'objet d'entré est null
        if (siteDTO == null) {
            return null;
        }

        final SiteEntity siteEntity = SiteEntity.builder()
                .id(siteDTO.getId())
                .nom(siteDTO.getNom().toUpperCase())
                .localite(siteDTO.getLocalite().toUpperCase())
                .dateCreation(siteDTO.getDateCreation())
                .dateModification(siteDTO.getDateModification())
                .build();
        return siteEntity;
    }
}
