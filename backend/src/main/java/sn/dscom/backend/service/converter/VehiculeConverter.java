package sn.dscom.backend.service.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.database.entite.VehiculeEntity;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class VehiculeConverter {
    @Autowired
    VehiculeConverter(){super();}
    public static VehiculeDTO toVehiculeDTO(@Valid VehiculeEntity vehiculeEntity) {
        //todo
        if (vehiculeEntity == null) {
            return null;
        }
        return VehiculeDTO.builder()
                .id(vehiculeEntity.getId())
                .immatriculation(vehiculeEntity.getImmatriculation())
                .nom(vehiculeEntity.getNom())
                .transporteur(TransporteurConverter.toTransporteurDTO(vehiculeEntity.getTransporteurEntity()))
                .dateCreation(vehiculeEntity.getId() == null ? new Date()  :vehiculeEntity.getDateCreation())
                .dateModification(vehiculeEntity == null ? null :vehiculeEntity.getDateModification())
                .build();
    }
    public static VehiculeEntity toVehiculeEntity(VehiculeDTO vehiculeDTO) {
        // todo
        if (vehiculeDTO == null) {
            return null;
        }

        VehiculeEntity vehiculeEntity = VehiculeEntity.builder()
                .id(vehiculeDTO.getId())
                .immatriculation(vehiculeDTO.getImmatriculation())
                .nom(vehiculeDTO.getNom())
                .transporteurEntity(TransporteurConverter.toTransporteurEntity(vehiculeDTO.getTransporteur()))
                .dateCreation(vehiculeDTO.getId() == null ? new Date() :vehiculeDTO.getDateCreation())
                .dateModification(vehiculeDTO == null ? null :vehiculeDTO.getDateModification())
                .build();
        return vehiculeEntity;
    }
}
