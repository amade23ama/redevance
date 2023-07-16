package sn.dscom.backend.service.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.CategorieEntity;

import java.util.Date;

/**
 * class de test {@link CategorieConverterTest}
 */
class CategorieConverterTest {

    /** categorie Converter */
    private Transformer<CategorieDTO, CategorieEntity> categorieConverter = new CategorieConverter();

    /**
     * reverse test()
     */
    @Test
    void reverse() {
        //GIVEN
        CategorieEntity categorieEntity = sn.dscom.backend.database.entite.CategorieEntity.builder()
                                                                                        .id(1L)
                                                                                        .dateCreation(new Date())
                                                                                        .dateModification(new Date())
                                                                                        .type("AUDI")
                                                                                        .volume(20.2)
                                                                                        .build();
        //WHEN
        CategorieDTO retour = this.categorieConverter.reverse(categorieEntity);

        //THEN
        Assertions.assertAll(
                () -> Assertions.assertEquals(categorieEntity.getId(), retour.getId()),
                () -> Assertions.assertEquals(categorieEntity.getVolume(), retour.getVolume()),
                () -> Assertions.assertEquals(categorieEntity.getType(), retour.getType()),
                () -> Assertions.assertEquals(categorieEntity.getDateCreation(), retour.getDateCreation()),
                () -> Assertions.assertEquals(categorieEntity.getDateModification(), retour.getDateModification()),
                () -> Assertions.assertEquals(categorieEntity.getDateCreation(), retour.getDateCreation())
        );
    }

    @Test
    void transform() {

        //GIVEN
        CategorieDTO categorieEntity = CategorieDTO.builder()
                .id(1L)
                .dateCreation(new Date())
                .dateModification(new Date())
                .type("AUDI")
                .volume(20.2)
                .build();
        //WHEN
        CategorieEntity retour = this.categorieConverter.transform(categorieEntity);

        //THEN
        Assertions.assertAll(
                () -> Assertions.assertEquals(categorieEntity.getId(), retour.getId()),
                () -> Assertions.assertEquals(categorieEntity.getVolume(), retour.getVolume()),
                () -> Assertions.assertEquals(categorieEntity.getType(), retour.getType()),
                () -> Assertions.assertEquals(categorieEntity.getDateCreation(), retour.getDateCreation()),
               // () -> Assertions.assertNotEquals(categorieEntity.getDateModification(), retour.getDateModification()),
                () -> Assertions.assertEquals(categorieEntity.getDateCreation(), retour.getDateCreation())
        );
    }
}