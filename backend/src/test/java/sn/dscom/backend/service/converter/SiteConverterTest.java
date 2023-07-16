package sn.dscom.backend.service.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.SiteEntity;

import java.util.Date;

/**
 * SiteConverterTest
 */
class SiteConverterTest {

    /** Site Converteur */
    private Transformer<SiteDTO, SiteEntity> siteConverteur = new SiteConverter();

    /**
     * reverse
     */
    @Test
    void reverse() {

        //GIVEN
        SiteEntity expected = SiteEntity.builder()
                .id(1l)
                .dateCreation(new Date())
                .dateModification(new Date())
                .localite("DAKAR")
                .nom("NOM")
                .build();

        //WHEN
        SiteDTO actual = siteConverteur.reverse(expected);

        //THEN

        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getId(), actual.getId()),
                () -> Assertions.assertEquals(expected.getDateModification(), actual.getDateModification()),
                () -> Assertions.assertEquals(expected.getDateCreation(), actual.getDateCreation()),
                () -> Assertions.assertEquals(expected.getNom(), actual.getNom()),
                () -> Assertions.assertEquals(expected.getLocalite(), actual.getLocalite())

        );
    }

    /**
     * transform
     */
    @Test
    void transform() {

        //GIVEN
        SiteDTO expected = SiteDTO.builder()
                .id(1l)
                .dateCreation(new Date())
                .dateModification(new Date())
                .localite("DAKAR")
                .nom("NOM")
                .build();

        //WHEN
        SiteEntity actual = siteConverteur.transform(expected);

        //THEN

        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getId(), actual.getId()),
                () -> Assertions.assertEquals(expected.getDateModification(), actual.getDateModification()),
                () -> Assertions.assertEquals(expected.getDateCreation(), actual.getDateCreation()),
                () -> Assertions.assertEquals(expected.getNom(), actual.getNom()),
                () -> Assertions.assertEquals(expected.getLocalite(), actual.getLocalite())

        );
    }
}