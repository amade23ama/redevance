package sn.dscom.backend.service.converter;

import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ProduitEntity;

/**
 * Produit Converter
 */
public class ProduitConverter implements Transformer<ProduitDTO,ProduitEntity> {

    /**
     * transformation de {@link ProduitEntity} en {@link ProduitDTO}
     *
     * @param produitEntity l'objet à transformer en {@link ProduitDTO}
     * @return l'objet {@link ProduitDTO}
     */
    @Override
    public ProduitDTO reverse(ProduitEntity produitEntity) {

        //on retourne null si l'objet d'entré est null
        if (produitEntity == null) {
            return null;
        }
        return ProduitDTO.builder()
                .id(produitEntity.getId())
                .nomNORM(produitEntity.getNomNORM())
                .nomSRC(produitEntity.getNomSRC())
                .densiteGRM(produitEntity.getDensiteGRM())
                .densiteKGM(produitEntity.getDensiteKGM())
                .dateCreation(produitEntity.getDateCreation())
                .dateModification(produitEntity.getDateCreation())
                .build();
    }

    /**
     * transformation de {@link ProduitDTO} en {@link ProduitEntity}
     *
     * @param produitDTO l'objet à transformer en {@link ProduitEntity}
     * @return l'objet {@link ProduitEntity}
     */
    @Override
    public ProduitEntity transform(ProduitDTO produitDTO) {

        //on retourne null si l'objet d'entré est null
        if (produitDTO == null) {
            return null;
        }
        return ProduitEntity.builder()
                .id(produitDTO.getId())
                .nomNORM(produitDTO.getNomNORM())
                .nomSRC(produitDTO.getNomSRC())
                .densiteGRM(produitDTO.getDensiteGRM())
                .densiteKGM(produitDTO.getDensiteKGM())
                .dateCreation(produitDTO.getDateCreation())
                .dateModification(produitDTO.getDateCreation())
                .build();
    }
}
