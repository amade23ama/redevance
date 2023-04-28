package sn.dscom.backend.service.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.dscom.backend.common.dto.ExploitationDTO;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.database.entite.ExploitationEntity;
import sn.dscom.backend.database.entite.ProduitEntity;

import javax.validation.Valid;
import java.util.Date;

@Component
public class ProduitConverter {
    @Autowired
    ProduitConverter(){super();}
    public static ProduitDTO toProduitDTO(@Valid ProduitEntity produitEntity) {
        //todo
        if (produitEntity == null) {
            return null;
        }
        ProduitDTO produitDTO=ProduitDTO.builder()
                .id(produitEntity.getId())
                .nomNORM(produitEntity.getNomNORM())
                .nomSRC(produitEntity.getNomSRC())
                .densiteGRM(produitEntity.getDensiteGRM())
                .densiteKGM(produitEntity.getDensiteKGM())
                .dateCreation(produitEntity.getId() == null ? new Date() :produitEntity.getDateCreation())
                .dateModification(produitEntity == null ? null :produitEntity.getDateCreation())
                .build();
        return produitDTO;
    }
    public static ProduitEntity toProduitEntity(ProduitDTO produitDTO) {
        // todo
        if (produitDTO == null) {
            return null;
        }
        final ProduitEntity produitEntity =ProduitEntity.builder()
                .id(produitDTO.getId())
                .nomNORM(produitDTO.getNomNORM())
                .nomSRC(produitDTO.getNomSRC())
                .densiteGRM(produitDTO.getDensiteGRM())
                .densiteKGM(produitDTO.getDensiteKGM())
                .dateCreation(produitDTO.getId() == null ? new Date()  :produitDTO.getDateCreation())
                .dateModification(produitDTO == null ? null :produitDTO.getDateCreation())
                .build();
        return produitEntity;
    }
}
