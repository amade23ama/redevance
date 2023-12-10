package sn.dscom.backend.service.converter;

import com.google.common.collect.MoreCollectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.entite.DepotEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DepotConverterTest {

    /**
     * explitation Converteur
     */
    Transformer<DepotDTO, DepotEntity> depotConverteur = new DepotConverter();

    /**
     * test reverse
     */
    @Test
    void reverse() {

        //GIVEN
        DepotEntity expected = buildDepotEntity();

        //WHEN
        DepotDTO actual = depotConverteur.reverse(expected);

        //THEN
        ChargementDTO chargementActual = actual.getChargementDTOList().stream().collect(MoreCollectors.onlyElement());
        //ChargementEntity chargementExpected = expected..stream().collect(MoreCollectors.onlyElement());
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getId(), actual.getId()),
                () -> Assertions.assertEquals(expected.getNom(), actual.getNom()),
                () -> Assertions.assertEquals(expected.getDateHeureDepot(), actual.getDateHeureDepot()),
                () -> Assertions.assertEquals(expected.getNbChargementDeposes(), actual.getNbChargementDeposes()),
                () -> Assertions.assertNotNull(chargementActual)
                //() -> Assertions.assertEquals(chargementExpected.getId(), chargementActual.getId()),
               // () -> Assertions.assertEquals(chargementExpected.getDestination(), chargementActual.getDestination())

        );
    }

    /**
     * test transform
     */
    @Test
    void transform() {

        //GIVEN
        DepotDTO expected = buildDepotDTO();

        //WHEN
        DepotEntity actual = depotConverteur.transform(expected);

        //THEN
        ChargementDTO chargementActual = expected.getChargementDTOList().stream().collect(MoreCollectors.onlyElement());
        //ChargementEntity chargementExpected = actual.getChargementEntityList().stream().collect(MoreCollectors.onlyElement());
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getId(), actual.getId()),
                () -> Assertions.assertEquals(expected.getNom(), actual.getNom()),
                () -> Assertions.assertEquals(expected.getDateHeureDepot(), actual.getDateHeureDepot()),
                () -> Assertions.assertEquals(expected.getNbChargementDeposes(), actual.getNbChargementDeposes()),
                () -> Assertions.assertNotNull(chargementActual)
                //() -> Assertions.assertEquals(chargementExpected.getId(), chargementActual.getId()),
                //() -> Assertions.assertEquals(chargementExpected.getDestination(), chargementActual.getDestination())

        );
    }

    /**
     * buildDepotEntity
     *
     * @return DepotEntity
     */
    private DepotEntity buildDepotEntity() {
        return DepotEntity.builder()
                .id(1L)
                .nom("nom".toUpperCase())
                .nbChargementErreur(0)
                .dateHeureDepot(new Date())
                .dateHeureDepot(new Date())
                .nbChargementDeposes(1)
                .nbChargementReDeposes(1)
                .statut("OK")
               /* .chargementEntityList(Arrays.asList(ChargementEntity.builder()
                                .id(1L)
                                .destination("DEST")
                        .build()))
                        */
                .build();
    }

    /**
     * buildDepotEntity
     *
     * @return DepotEntity
     */
    private DepotDTO buildDepotDTO() {
        return DepotDTO.builder()
                .id(1L)
                .nom("nom".toUpperCase())
                .nbChargementErreur(0)
                .dateHeureDepot(new Date())
                .dateHeureDepot(new Date())
                .nbChargementDeposes(1)
                .nbChargementReDeposes(1)
                /*.chargementDTOList(Arrays.asList(ChargementDTO.builder()
                        .id(1L)
                        .destination("DEST")
                        .build()))
                */
                .build();
    }

}
