package sn.dscom.backend.service.converter;

import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.DepotEntity;

import java.util.Date;

/**
 * Exploitation Converter
 */
public class DepotConverter implements Transformer<DepotDTO, DepotEntity> {


    /**
     * transformation de {@link DepotEntity} en {@link DepotDTO}
     *
     * @param depotEntity l'objet à transformer en {@link DepotDTO}
     * @return l'objet {@link DepotDTO}
     */
    @Override
    public DepotDTO reverse(DepotEntity depotEntity) {

        //on retourne null si l'objet d'entré est null
        if (depotEntity == null) {
            return null;
        }
        return DepotDTO.builder()
                .id(depotEntity.getId())
                .nom(depotEntity.getNom())
                .dateHeureDepot(depotEntity.getDateHeureDepot())
                .dateHeureFinDepot(depotEntity.getDateHeureFinDepot())
               // .deposeur(depotEntity.getDeposeur())
                .nbChargementDeposes(depotEntity.getNbChargementDeposes())
                .nbChargementErreur(depotEntity.getNbChargementErreur())
                .nomFichier(depotEntity.getNomFichier())
                .nbChargementReDeposes(depotEntity.getNbChargementReDeposes())
                .build();
    }

    /**
     * transformation de {@link DepotDTO} en {@link DepotEntity}
     *
     * @param depotDTO l'objet à transformer en {@link DepotEntity}
     * @return l'objet {@link DepotEntity}
     */
    @Override
    public DepotEntity transform(DepotDTO depotDTO) {
        //on retourne null si l'objet d'entré est null
        if (depotDTO == null) {
            return null;
        }
        return DepotEntity.builder()
                .id(depotDTO.getId())
                .nom(depotDTO.getNom())
                .dateHeureDepot(depotDTO.getId() == null ? new Date() :depotDTO.getDateHeureDepot())
                .dateHeureFinDepot(depotDTO.getId() == null ? new Date() :depotDTO.getDateHeureDepot()) //TODO
                // .deposeur(depotEntity.getDeposeur())
                .nbChargementDeposes(depotDTO.getNbChargementDeposes())
                .nbChargementErreur(depotDTO.getNbChargementErreur())
                .nomFichier(depotDTO.getNomFichier())
                .nbChargementReDeposes(depotDTO.getNbChargementReDeposes())
                .build();
    }
}
