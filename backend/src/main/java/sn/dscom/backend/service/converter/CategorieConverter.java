package sn.dscom.backend.service.converter;

import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.CategorieEntity;

import java.util.Date;

/**
 * Converteur de {@link CategorieEntity} en {@link CategorieDTO}
 */
public class CategorieConverter implements Transformer<CategorieDTO, CategorieEntity> {

    /**
     * transformation de {@link CategorieEntity} en {@link CategorieDTO}
     *
     * @param categorieEntity l'objet à transformer en {@link CategorieDTO}
     * @return l'objet {@link CategorieDTO}
     */
    @Override
    public CategorieDTO reverse(CategorieEntity categorieEntity) {

        //on retourne null si l'objet d'entré est null
        if (categorieEntity == null) {
            return null;
        }

        return CategorieDTO.builder()
                            .id(categorieEntity.getId())
                            .dateCreation(categorieEntity.getDateCreation())
                            .dateModification(categorieEntity.getDateModification())
                            .type(categorieEntity.getType())
                            .volume(categorieEntity.getVolume())
                            .build();
    }

    /**
     * transformation de {@link CategorieDTO} en {@link CategorieEntity}
     *
     * @param categorieDTO l'objet à transformer en {@link CategorieEntity}
     * @return l'objet {@link CategorieEntity}
     */
    @Override
    public CategorieEntity transform(CategorieDTO categorieDTO) {

        //on retourne null si l'objet d'entré est null
        if (categorieDTO == null) {
            return null;
        }

        return CategorieEntity.builder()
                                .id(categorieDTO.getId())
                                .dateCreation(categorieDTO.getId() == null ? new Date() : categorieDTO.getDateCreation())
                                .dateModification(categorieDTO.getId() == null ? null : new Date())
                                .type(categorieDTO.getType())
                                .volume(categorieDTO.getVolume())
                                .build();
    }
}
