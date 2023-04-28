package sn.dscom.backend.service.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.entite.TransporteurEntity;

import javax.validation.Valid;
import java.util.Date;

@Component
public class TransporteurConverter {
    @Autowired
    TransporteurConverter(){super();}
    public static TransporteurDTO toTransporteurDTO(@Valid TransporteurEntity transporteurEntity) {
        //todo
        if (transporteurEntity == null) {
            return null;
        }
        return TransporteurDTO.builder()
                .id(transporteurEntity.getId())
                .nom(transporteurEntity.getNom())
                .prenom(transporteurEntity.getPrenom())
                .email(transporteurEntity.getEmail())
                .telephone(transporteurEntity.getTelephone())
                .adresse(transporteurEntity.getAdresse())
                .dateCreation(transporteurEntity.getId() == null ? new Date() :transporteurEntity.getDateCreation())
                .dateModification(transporteurEntity == null ? null :transporteurEntity.getDateModification())
                .build();
    }
    public static TransporteurEntity toTransporteurEntity(TransporteurDTO transporteurDTO) {
        // todo
        final TransporteurEntity transporteurEntity =TransporteurEntity.builder()
                .id(transporteurDTO.getId())
                .nom(transporteurDTO.getNom())
                .prenom(transporteurDTO.getPrenom())
                .email(transporteurDTO.getEmail())
                .telephone(transporteurDTO.getTelephone())
                .adresse(transporteurDTO.getAdresse())
                .dateCreation(transporteurDTO.getId() == null ? new Date() :transporteurDTO.getDateCreation())
                .dateModification(transporteurDTO == null ? null :transporteurDTO.getDateModification())
                .vehiculeEntityListes(transporteurDTO.getVehiculeEntityListes())
                .build();

        return transporteurEntity;
    }
}
