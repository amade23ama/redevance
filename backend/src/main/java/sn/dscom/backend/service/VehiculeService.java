package sn.dscom.backend.service;

import io.vavr.control.Try;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.AutocompleteRecherche;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.TransporteurEntity;
import sn.dscom.backend.database.entite.VehiculeEntity;
import sn.dscom.backend.database.repository.TransporteurRepository;
import sn.dscom.backend.database.repository.VehiculeRepository;
import sn.dscom.backend.service.converter.VehiculeConverter;
import sn.dscom.backend.service.interfaces.ITransporteurService;
import sn.dscom.backend.service.interfaces.IVoitureService;
import sn.dscom.backend.service.util.VehiculeSpecifications;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service VehiculeService
 */
@Slf4j
@Transactional
public class VehiculeService implements IVoitureService{

    /** Repo vehiculeRepository */
    private final VehiculeRepository vehiculeRepository;

    /**
     * vehicule Converter
     */
    private final Transformer<VehiculeDTO, VehiculeEntity> vehiculeConverter = new VehiculeConverter();

    /** transporteur Repository */
    private final TransporteurRepository transporteurRepository;

    /**
     * VehiculeService
     * @param vehiculeRepository vehiculeRepository
     */
    @Builder
    public VehiculeService(VehiculeRepository vehiculeRepository, TransporteurRepository transporteurRepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.transporteurRepository = transporteurRepository;
    }

    /**
     * Fournit l'operation d'enregistrement d'un véhicule
     *
     * @param vehiculeDTO le véhicule à enregistrer
     * @return le véhicule enregistrée
     */
    @Override
    public Optional<VehiculeDTO> enregistrerVehicule(VehiculeDTO vehiculeDTO) {

        // Vérifiacation
        VehiculeEntity vehiculeEntity = this.vehiculeRepository.isVehiculeExist(vehiculeDTO.getImmatriculation().toUpperCase());

        // s'il existe on renvoit le site existant
        if(vehiculeEntity != null && vehiculeDTO.getId() == null ){
            return Optional.of(this.vehiculeConverter.reverse(vehiculeEntity));
        }

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
    @Override
    public Optional<VehiculeDTO> modifierVehicule(VehiculeDTO vehiculeDTO) {

        // On récupère le véhicule à modifier
        Optional<VehiculeEntity> vehiculeFind = this.vehiculeRepository.findById(vehiculeDTO.getId());

        // s'il existe, on l'a modifie
        if (vehiculeFind.isPresent()){
            // on modifie le transpoteur lié au véhicule
            TransporteurDTO transporteurDTO = vehiculeDTO.getTransporteur();

            Optional<TransporteurEntity> transporteurEntityOptional = transporteurRepository.findById(transporteurDTO.getId());
            if (transporteurEntityOptional.isPresent()) {
                TransporteurEntity transporteurEntityToSave = transporteurEntityOptional.get();
                transporteurEntityToSave.setVehiculeEntityListes(null);
                transporteurEntityToSave.setNom(transporteurDTO.getNom());
                transporteurEntityToSave.setTelephone(transporteurDTO.getTelephone());
                transporteurEntityToSave.setDateModification( new Date());

                Try.of(() -> transporteurEntityToSave)
                        .mapTry(this.transporteurRepository::save)
                        .onFailure(e -> TransporteurService.logger.info(String.format("Erreur de l'nregistrement du Transporteur: %s", e.getMessage())))
                        .get();
            }

            return Optional.of(this.vehiculeConverter.reverse(Try.of(() -> this.vehiculeConverter.transform(vehiculeDTO))
                    .mapTry(this.vehiculeRepository::save).get()));
        }

        throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
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

    @Override
    public VehiculeDTO chargerVehiculeDTOParId(Long id) {
        Optional<VehiculeEntity> vehicule = this.vehiculeRepository.findById(id);
        if (vehicule.isPresent()) {
            return this.vehiculeConverter.reverse(vehicule.get());
        } else {
            throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
        }
    }

    /**
     * rechargement Par Critere
     *
     * @param critereRecherche critereRecherche
     * @return liste
     */
    @Override
    public List<VehiculeDTO> rechargementParCritere(CritereRecherche<?> critereRecherche) {

        //S'il n'y a pas de critère on remonte tout
        if (critereRecherche.getAutocompleteRecherches().size() == 0){
            /** find all de tous les véhicule*/
            List<VehiculeEntity> list = this.vehiculeRepository.findAll();

            return list.stream()
                    .map(vehiculeEntity ->  vehiculeConverter.reverse(vehiculeEntity))
                    .collect(Collectors.toList());
        }

        List<Long> idsVehicule = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == VehiculeEntity.class)
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());

        List<Long> idsCategorie = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == CategorieEntity.class)
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());
        List<Long> volumes = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == String.class)
                .filter(item -> ((AutocompleteRecherche) item).getOrigine().equals("Volume"))
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());

        Specification<VehiculeEntity> spec = Specification
                .where(VehiculeSpecifications.withVehiculeIdsAndCategorieIds(idsVehicule,idsCategorie,
                        volumes));

        List<VehiculeEntity> listVehiculeFind= this.vehiculeRepository.findAll(spec);
        return listVehiculeFind.stream()
                .map(this.vehiculeConverter::reverse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }
}
