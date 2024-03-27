package sn.dscom.backend.service.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.TransporteurEntity;
import sn.dscom.backend.database.entite.VehiculeEntity;

import java.util.Date;

/**
 * Vehicule ConverterTest
 */
class VehiculeConverterTest {

    /**
     * vehicule Converter
     */
    private final Transformer<VehiculeDTO, VehiculeEntity> vehiculeConverter = new VehiculeConverter();

    /**
     * reverse test
     */
    @Test
    void reverse() {
        //GIVEN

        VehiculeEntity expected = VehiculeEntity.builder()
                .id(1L)
                .immatriculation("AA-123-NN")
                .categorieEntity(CategorieEntity.builder()
                        .id(1L)
                        .dateCreation(new Date())
                        .dateModification(new Date())
                        .type("AUDI")
                        .volume(20.2)
                        .build())
               /* .transporteurEntity(TransporteurEntity.builder()
                        .id(1L)
                        .dateCreation(new Date())
                        .dateModification(new Date())
                        .type("1DH")
                        .adresse("Dakar".toUpperCase())
                        .email("test@test.sn")
                        .nom("NOM")
                        .prenom("PRENOM")
                        .build())
                */
                .dateCreation(new Date())
                .dateModification(new Date())
                .build();

        //WHEN
        VehiculeDTO actual = this.vehiculeConverter.reverse(expected);
        //THEN
        Assertions.assertAll(
                () -> Assertions.assertEquals(actual.getId(), expected.getId()),
                () -> Assertions.assertEquals(actual.getDateCreation(), expected.getDateCreation()),
                () -> Assertions.assertEquals(actual.getDateModification(), expected.getDateModification()),
               // () -> Assertions.assertEquals(actual.getTransporteur().getPrenom(), expected.getTransporteurEntity().getPrenom()),
                () -> Assertions.assertEquals(actual.getCategorie().getId(), expected.getCategorieEntity().getId())
        );

    }

    /**
     * reverse test
     */
    @Test
    void transform() {
        //GIVEN

        VehiculeDTO expected = VehiculeDTO.builder()
                .id(1L)
                .immatriculation("AA-123-NN")
                .categorie(CategorieDTO.builder()
                        .id(1L)
                        .dateCreation(new Date())
                        .dateModification(new Date())
                        .type("AUDI")
                        .volume(20.2)
                        .build())
               /* .transporteur(TransporteurDTO.builder()
                        .id(1L)
                        .dateCreation(new Date())
                        .dateModification(new Date())
                        .type("1DH")
                        .adresse("Dakar".toUpperCase())
                        .email("test@test.sn")
                        .nom("NOM")
                        .prenom("PRENOM")
                        .build())
                */
                .dateCreation(new Date())
                .dateModification(new Date())
                .build();

        //WHEN
        VehiculeEntity actual = this.vehiculeConverter.transform(expected);
        //THEN
        Assertions.assertAll(
                () -> Assertions.assertEquals(actual.getId(), expected.getId()),
                () -> Assertions.assertEquals(actual.getDateCreation(), expected.getDateCreation()),
                //() -> Assertions.assertNotEquals(actual.getDateModification(), expected.getDateModification()),
               // () -> Assertions.assertEquals(actual.getTransporteurEntity().getPrenom(), expected.getTransporteur().getPrenom()),
                () -> Assertions.assertEquals(actual.getCategorieEntity().getId(), expected.getCategorie().getId())
        );
    }
}
