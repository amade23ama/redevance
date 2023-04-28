package sn.dscom.backend.service.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.dto.ProfilDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.database.entite.ProduitEntity;
import sn.dscom.backend.database.entite.ProfilEntity;

import javax.validation.Valid;

@Component
public class ProfilConverter {
    @Autowired
    ProfilConverter(){super();}
    public static ProfilDTO toProfilDTO( @Valid ProfilEntity profilEntity) {
        //todo
        if (profilEntity == null) {
            return null;
        }
        return ProfilDTO.builder()
                .code(profilEntity.getCode())
                .libelle(profilEntity.getLibelle())
                .build();
    }
    public static ProfilEntity toProfilEntity(ProfilDTO profilDTO) {
        // todo
        
        if (profilDTO == null) {
            return null;
        }
       ProfilEntity profilEntity =ProfilEntity.builder()
                .code(profilDTO.getCode())
                .libelle(profilDTO.getLibelle())
                .build();
        return profilEntity;
    }
}
