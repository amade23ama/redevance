package sn.dscom.backend.service.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import sn.dscom.backend.common.dto.ProfilDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.database.entite.ProfilEntity;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.entite.TransporteurEntity;
import sn.dscom.backend.database.entite.VehiculeEntity;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        final List<VehiculeEntity> listeVehicules = new ArrayList<>();
        if (!CollectionUtils.isEmpty(transporteurDTO.getVehiculeListes())) {
            transporteurDTO.getVehiculeListes()
                    .forEach((VehiculeDTO vehiculeDTO) ->
                            listeVehicules.add(VehiculeConverter.toVehiculeEntity(vehiculeDTO)));
        }
        final TransporteurEntity transporteurEntity =TransporteurEntity.builder()
                .id(transporteurDTO.getId())
                .nom(transporteurDTO.getNom())
                .prenom(transporteurDTO.getPrenom())
                .email(transporteurDTO.getEmail())
                .telephone(transporteurDTO.getTelephone())
                .adresse(transporteurDTO.getAdresse())
                .dateCreation(transporteurDTO.getId() == null ? new Date() :transporteurDTO.getDateCreation())
                .dateModification(transporteurDTO == null ? null :transporteurDTO.getDateModification())
                .vehiculeEntityListes(listeVehicules)
                .build();
        return transporteurEntity;
    }
}
