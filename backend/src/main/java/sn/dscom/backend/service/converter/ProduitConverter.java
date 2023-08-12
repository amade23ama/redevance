package sn.dscom.backend.service.converter;

import com.google.common.base.Strings;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ProduitEntity;

import java.util.Date;

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
                .nomNORM(Strings.isNullOrEmpty(produitDTO.getNomNORM()) ? produitDTO.getNomNORM() : produitDTO.getNomNORM().trim().toUpperCase())
                .nomSRC(Strings.isNullOrEmpty(produitDTO.getNomSRC()) ? produitDTO.getNomSRC() : produitDTO.getNomSRC().trim().toUpperCase())
                .densiteGRM(produitDTO.getDensiteGRM())
                .densiteKGM(produitDTO.getDensiteKGM())
                .dateCreation(produitDTO.getId() == null ? new Date() :produitDTO.getDateCreation())
                .dateModification(produitDTO.getId() == null? null :produitDTO.getDateModification())
                .build();
    }
}
