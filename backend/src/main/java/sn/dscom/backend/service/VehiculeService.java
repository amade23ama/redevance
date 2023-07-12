package sn.dscom.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.VehiculeEntity;
import sn.dscom.backend.database.repository.VehiculeRepository;
import sn.dscom.backend.service.converter.VehiculeConverter;
import sn.dscom.backend.service.interfaces.IVoitureService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service VehiculeService
 */
@Service
@Transactional
public class VehiculeService implements IVoitureService{

    /** Repo vehiculeRepository */
    @Autowired
    private VehiculeRepository vehiculeRepository;

    /**
     * vehicule Converter
     */
    Transformer<VehiculeDTO, VehiculeEntity> vehiculeConverter = new VehiculeConverter();

    /*@Transactional
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
    }*/

    /**
     * Fournit l'operation d'enregistrement d'un véhicule
     *
     * @param vehiculeDTO le véhicule à enregistrer
     * @return le véhicule enregistrée
     */
    @Override
    public Optional<VehiculeDTO> enregistrerVehicule(VehiculeDTO vehiculeDTO) {
        return Optional.empty();
    }

    /**
     * Fournit l'operation de rechercher d'un véhicule
     *
     * @return la liste des véhicules trouvées
     */
    @Transactional
    @Override
    public Optional<List<VehiculeDTO>> rechercherVehicules() {

        /** find all de tous les véhicule*/
        List<VehiculeEntity> list = this.vehiculeRepository.findAll();

        return Optional.of( list.stream()
                               .map(vehiculeEntity ->  vehiculeConverter.reverse(vehiculeEntity))
                               .collect(Collectors.toList())
                );
    }

    /**
     * Fournit l'operation de modification d'un véhicule
     *
     * @param vehiculeDTO le véhicule à modifier
     * @return le véhicule modifié
     */
    @Override
    public Optional<VehiculeDTO> modifierVehicule(VehiculeDTO vehiculeDTO) {
        return Optional.empty();
    }

    /**
     * Fournit l'operation de suppression d'un véhicule
     *
     * @param id de l'Entité à supprimer
     */
    @Override
    public void supprimerVehicule(String id) {

    }
}
