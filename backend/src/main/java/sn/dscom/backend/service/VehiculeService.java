package sn.dscom.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.VehiculeEntity;
import sn.dscom.backend.database.repository.VehiculeRepository;
import sn.dscom.backend.service.converter.VehiculeConverter;
import sn.dscom.backend.service.interfaces.IVoitureService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service VehiculeService
 */
@Slf4j
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

    /**
     * Fournit l'operation d'enregistrement d'un véhicule
     *
     * @param vehiculeDTO le véhicule à enregistrer
     * @return le véhicule enregistrée
     */
    @Override
    public Optional<VehiculeDTO> enregistrerVehicule(VehiculeDTO vehiculeDTO) {

        // en cas de création id est null ou vide
        if (null == vehiculeDTO.getId()){
            //on ajout la date de création et on enregistre
            vehiculeDTO.setDateCreation(new Date());
            return Optional.of(this.vehiculeConverter.reverse(vehiculeRepository.save(this.vehiculeConverter.transform(vehiculeDTO))));
        }

        // Cas de modification
        final Optional<VehiculeEntity> entityVehicule = vehiculeRepository.findById(vehiculeDTO.getId());
        if(!entityVehicule.isPresent()){
            throw new RuntimeException("L'entité à modifier n'existe pas en base");
        }
        vehiculeDTO.setDateModification(new Date());
        return Optional.of(this.vehiculeConverter.reverse(vehiculeRepository.save(this.vehiculeConverter.transform(vehiculeDTO))));
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
    //TODO: completer un retour (nouvelle recherche avec l'id et retourner le resulta)
    @Override
    public Optional<VehiculeDTO> modifierVehicule(VehiculeDTO vehiculeDTO) {

        try {
            // Date du jour pour la date de modification;
            this.vehiculeRepository.miseAjourImmatParId(vehiculeDTO.getImmatriculation(), vehiculeDTO.getId(), new Date());

        }catch (Exception e){
            log.error("exception sur la modification de l'entité avec id: {} ",vehiculeDTO.getId(), e);
        }
        return Optional.of(VehiculeDTO.builder().build());
    }

    /**
     * Fournit l'operation de suppression d'un véhicule
     *
     * On ne peut pas supprimer une entité voiture en l'état vu les contrainte sur la table chargement
     *
     * @param voiture de l'Entité à supprimer
     */
    @Override
    public void supprimerVehicule(VehiculeDTO voiture) {
        // Suppression de l'lélément en base
        try {
            this.vehiculeRepository.deleteParIdentifiant(voiture.getId());
            log.info("L'entité avec id {} a été bien supprimée.",voiture.getId());
        }catch (Exception e){
            log.error("exception sur la suppression de l'entité avec id: {} ",voiture.getId(), e);
        }

    }
}
