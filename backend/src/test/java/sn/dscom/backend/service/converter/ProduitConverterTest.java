package sn.dscom.backend.service.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.ProduitEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class de test {@link ProduitConverterTest}
 */
class ProduitConverterTest {

    /** categorie Converter */
    private Transformer<ProduitDTO, ProduitEntity> produitConverter = new ProduitConverter();

    /**
     * test reverse
     */
    @Test
    void reverse() {

        //GIVEN
        ProduitEntity expected = this.buildProduitEntity();

        //WHEN
        ProduitDTO actual = produitConverter.reverse(expected);
        //THEN

        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getId(), actual.getId()),
                () -> Assertions.assertEquals(expected.getDateCreation(), actual.getDateCreation()),
                () -> Assertions.assertEquals(expected.getDateModification(), actual.getDateModification()),
                () -> Assertions.assertEquals(expected.getNomNORM(), actual.getNomNORM()),
                () -> Assertions.assertEquals(expected.getNomSRC(), actual.getNomSRC()),
                () -> Assertions.assertEquals(expected.getDensiteGRM(), actual.getDensiteGRM()),
                () -> Assertions.assertEquals(expected.getDensiteKGM(), actual.getDensiteKGM())
        );
    }

    /**
     * buildProduitEntity
     * @return ProduitEntity
     */
    private ProduitEntity buildProduitEntity() {

        return  ProduitEntity.builder()
                .id(1L)
                .dateCreation(new Date())
                .dateModification(new Date())
                .densiteGRM(1.2)
                .densiteKGM(2.1)
                .nomNORM("nom1")
                .nomSRC("SRC")
                .build();
    }

    /**
     * buildProduitEntity
     * @return ProduitEntity
     */
    private ProduitDTO buildProduitDTO() {

        return  ProduitDTO.builder()
                .id(1L)
                .dateCreation(new Date())
                .dateModification(new Date())
                .densiteGRM(1.2)
                .densiteKGM(2.1)
                .nomNORM("nom1")
                .nomSRC("SRC")
                .build();
    }

    /**
     * test transform
     */
    @Test
    void transform() {
        //GIVEN
        ProduitDTO expected = this.buildProduitDTO();

        //WHEN
        ProduitEntity actual = produitConverter.transform(expected);
        //THEN

        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getId(), actual.getId()),
                () -> Assertions.assertEquals(expected.getDateCreation(), actual.getDateCreation()),
                () -> Assertions.assertEquals(expected.getDateModification(), actual.getDateModification()),
                () -> Assertions.assertEquals(expected.getNomNORM(), actual.getNomNORM()),
                () -> Assertions.assertEquals(expected.getNomSRC(), actual.getNomSRC()),
                () -> Assertions.assertEquals(expected.getDensiteGRM(), actual.getDensiteGRM()),
                () -> Assertions.assertEquals(expected.getDensiteKGM(), actual.getDensiteKGM())
        );
    }
}