package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.entite.VehiculeEntity;
import sn.dscom.backend.database.repository.VehiculeRepository;
import sn.dscom.backend.service.converter.SiteConverter;
import sn.dscom.backend.service.converter.VehiculeConverter;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class VehiculeService {

    private VehiculeRepository vehiculeRepository;
    @Transactional
    private VehiculeDTO miseAjourVehicule(VehiculeDTO vehiculeDTO) {
        //todo
        VehiculeEntity vehiculeEntity = VehiculeConverter.toVehiculeEntity(vehiculeDTO);
        final Optional<VehiculeEntity> entityVehicule = vehiculeRepository.findById(vehiculeDTO.getId());
        if(entityVehicule.isPresent()){
            vehiculeEntity.setDateModification(new Date());
        }else {
            vehiculeEntity.setDateCreation(new Date());
        }
        vehiculeEntity =vehiculeRepository.save(vehiculeEntity);
        return  VehiculeConverter.toVehiculeDTO(vehiculeEntity);
    }
}
