package sn.dscom.backend.service.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.TransporteurEntity;

import java.util.Date;

/**
 * Transporteur ConverterTest
 */
class TransporteurConverterTest {

    /** transporteur Converter*/
    private final Transformer<TransporteurDTO, TransporteurEntity> transporteurConverter = new TransporteurConverter();

    /**
     * reverse test
     */
    @Test
    void reverse() {
        //GIVEN
        TransporteurEntity expected = TransporteurEntity.builder()
                                                        .id(1L)
                                                        .dateCreation(new Date())
                                                        .dateModification(new Date())
                                                        .type("1DH")
                                                        .adresse("Dakar".toUpperCase())
                                                        .email("test@test.sn")
                                                        .nom("NOM".toUpperCase())
                                                        .prenom("PRENOM".toUpperCase())
                                                        .build();

        //WHEN
        TransporteurDTO actual = this.transporteurConverter.reverse(expected);

        //THEN
        Assertions.assertAll(
                () -> Assertions.assertEquals(actual.getId(), expected.getId()),
                () -> Assertions.assertEquals(actual.getDateCreation(), expected.getDateCreation()),
                () -> Assertions.assertEquals(actual.getDateModification(), expected.getDateModification()),
                () -> Assertions.assertEquals(actual.getType(), expected.getType()),
                () -> Assertions.assertEquals(actual.getAdresse(), expected.getAdresse()),
                () -> Assertions.assertEquals(actual.getEmail(), expected.getEmail()),
                () -> Assertions.assertEquals(actual.getNom(), expected.getNom()),
                () -> Assertions.assertEquals(actual.getPrenom(), expected.getPrenom())
        );
    }

    /**
     * transform test
     */
    @Test
    void transform() {

        //GIVEN
        TransporteurDTO expected = TransporteurDTO.builder()
                                                        .id(1L)
                                                        .dateCreation(new Date())
                                                        .dateModification(new Date())
                                                        .type("1DH")
                                                        .adresse("Dakar".toUpperCase())
                                                        .email("test@test.sn")
                                                        .nom("NOM".toUpperCase())
                                                        .prenom("PRENOM".toUpperCase())
                                                        .build();

        //WHEN
        TransporteurEntity actual = this.transporteurConverter.transform(expected);

        //THEN
        Assertions.assertAll(
                () -> Assertions.assertEquals(actual.getId(), expected.getId()),
                () -> Assertions.assertEquals(actual.getDateCreation(), expected.getDateCreation()),
                //() -> Assertions.assertNotEquals(actual.getDateModification(), expected.getDateModification()),
                () -> Assertions.assertEquals(actual.getType(), expected.getType()),
                () -> Assertions.assertEquals(actual.getAdresse(), expected.getAdresse()),
                () -> Assertions.assertEquals(actual.getEmail(), expected.getEmail()),
                () -> Assertions.assertEquals(actual.getNom(), expected.getNom()),
                () -> Assertions.assertEquals(actual.getPrenom(), expected.getPrenom())
        );
    }
}
