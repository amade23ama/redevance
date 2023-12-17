package sn.dscom.backend.service;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import io.vavr.control.Try;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.Utils;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.VehiculeEntity;
import sn.dscom.backend.database.repository.CategorieRepository;
import sn.dscom.backend.database.repository.TransporteurRepository;
import sn.dscom.backend.database.repository.VehiculeRepository;
import sn.dscom.backend.service.converter.CategorieConverter;
import sn.dscom.backend.service.converter.VehiculeConverter;
import sn.dscom.backend.service.interfaces.IVoitureService;
import sn.dscom.backend.service.util.VehiculeSpecifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    private final CategorieRepository categorieRepository;

    /**
     * vehicule Converter
     */
    private final Transformer<VehiculeDTO, VehiculeEntity> vehiculeConverter = new VehiculeConverter();
    private final Transformer<CategorieDTO, CategorieEntity> categorieConverter = new CategorieConverter();
    /** transporteur Repository */
    private final TransporteurRepository transporteurRepository;

    /**
     * VehiculeService
     * @param vehiculeRepository vehiculeRepository
     */
    @Builder
    public VehiculeService(VehiculeRepository vehiculeRepository, TransporteurRepository transporteurRepository,
                           CategorieRepository categorieRepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.transporteurRepository = transporteurRepository;
        this.categorieRepository =categorieRepository;
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
    public Page<VehiculeDTO> rechargementParCritere(CritereRecherche<?> critereRecherche) {
        PageRequest pageRequest = PageRequest.of(critereRecherche.getPage(), critereRecherche.getSize());
        //S'il n'y a pas de critère on remonte tout
        if (critereRecherche.getAutocompleteRecherches().size() == 0){
            Page<VehiculeEntity> list = this.vehiculeRepository.findAll(pageRequest);

            List<VehiculeDTO> listVehicule = list.getContent().stream()
                    .map(vehiculeConverter::reverse)
                    .toList();
            return new PageImpl<>(listVehicule, pageRequest, list.getTotalElements());
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

        Page<VehiculeEntity> listVehiculeFind= this.vehiculeRepository.findAll(spec,pageRequest);
        List<VehiculeDTO> listVehicule = listVehiculeFind.getContent().stream()
                .map(this.vehiculeConverter::reverse)
                .filter(Objects::nonNull)
                .toList();

        return new PageImpl<>(listVehicule, pageRequest, listVehiculeFind.getTotalElements());
    }

    @Override
    public VehiculeDTO rechercherVehiculeByMatricule(String matricule) {
        VehiculeEntity vehicule = this.vehiculeRepository.isVehiculeExist(matricule.toUpperCase());
        if (vehicule!=null) {
            return this.vehiculeConverter.reverse(vehicule);
        } else {
            return null;
        }
    }

    @Override
    public VehiculeDTO rechercherVehiculeByMatriculeAndIdTransporteurAndIdCategorie(String matricule, long idCategorie) {
        VehiculeEntity vehicule = this.vehiculeRepository. rechercherVehiculeByMatriculeAndIdTransporteurAndIdCategorie(matricule.toUpperCase(),idCategorie);
        if (vehicule!=null) {
            return this.vehiculeConverter.reverse(vehicule);
        } else {
            return null;
        }
    }

    @Override
    public VehiculeDTO saveVehicule(VehiculeDTO vehiculeDTO) {
        VehiculeEntity vehicule = this.vehiculeRepository.saveAndFlush(this.vehiculeConverter.transform(vehiculeDTO));
        return this.vehiculeConverter.reverse(vehicule);
    }

    @Override
    public void ChargementVehicule(MultipartFile file) {
        long timer1 = System.currentTimeMillis();
       int  totalChargement=0;
       int lNbChargementDoublons=0;
       int lNbChargementReDeposes=0;
        int i =1;
        try {
            List<VehiculeDTO> vehiculeDTOList= this.readFileMultipart(file);
            totalChargement=vehiculeDTOList.size();
            Set<VehiculeDTO> listVehiculeDTONewUnique = new HashSet<>(vehiculeDTOList);
            lNbChargementDoublons=totalChargement-listVehiculeDTONewUnique.size();
            List<VehiculeEntity> listVehiculeEntityNewUnique=this.buildVehicule(listVehiculeDTONewUnique.stream().toList());
            List<VehiculeEntity> listVehiculeEntitySave = new ArrayList<>();
            lNbChargementReDeposes=listVehiculeEntityNewUnique.stream().parallel().filter(vehiculeEntity ->vehiculeEntity.getId()!=null ).toList().size();
            for(VehiculeEntity vehiculeEntity:listVehiculeEntityNewUnique.stream().toList()){
                if(vehiculeEntity.getId()!=null){
                    log.info("Mise a jour matricule {} - id {} - line {}  ",vehiculeEntity.getImmatriculation(),vehiculeEntity.getId(),i);
                }else{
                    log.info("Enregistrement matricule {} - line {}  ",vehiculeEntity.getImmatriculation(),i);
                }

                listVehiculeEntitySave.add(vehiculeEntity);
                if(i%10==0){
                    this.vehiculeRepository.saveAll(listVehiculeEntitySave);
                    listVehiculeEntitySave.clear();
                }
                i++;
            }
            if(!listVehiculeEntitySave.isEmpty()){
                this.vehiculeRepository.saveAll(listVehiculeEntitySave);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("total Chargement         :{}",totalChargement);
        log.info("nombre Vehicule Doublons :{}",lNbChargementDoublons);
        log.info("nombre Vehicule Deposes  :{}",(i-1));
        log.info("nombre Vehicule ReDeposes:{}",lNbChargementReDeposes);
        long timer2 = System.currentTimeMillis();
        log.info("----duree----: " +(timer2-timer1) +"ms");
        log.info("----Fin -------------------: ");
    }


    VehiculeDTO  convertLineToJournal(String[] line){
        if (line.length < 3 && line.length>3) {
            return null;
        }
        return VehiculeDTO.builder()
                 .immatriculation(Utils.supprimerCaracteresSpeciaux(line[0].trim()))
                 .categorie(CategorieDTO.builder()
                         .type(Utils.supprimerCaracteresSpeciaux(line[1].trim()))
                         .build())
                 .poidsVide(Double.valueOf(line[2].trim().replace(",", ".")))
                 .build();
    }


    private List<VehiculeDTO>  readFileMultipart(MultipartFile file) throws Exception{
        log.info("Lecture du fichier {}",file.getName());
        List<VehiculeDTO> vehiculeDTOList = new ArrayList<>();
        try  {
            CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                    .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                    .build();
            List<String[]> csvData = csvReader.readAll();
            List<VehiculeDTO> listVehicule=csvData.parallelStream()
                    .skip(1)
                    .filter(chargement -> !Arrays.stream(chargement).allMatch(String::isEmpty))
                    .map(chargement -> convertLineToJournal(chargement))
                    .collect(Collectors.toList());

            vehiculeDTOList.addAll(listVehicule);

        }
        catch (Exception e){
            log.error("Erreur de lecture du fichier", e);
            return Collections.emptyList();
        }
        return vehiculeDTOList;
    }

    private List<VehiculeEntity> buildVehicule( List<VehiculeDTO> vehiculeDTOList){
        List<VehiculeEntity> listVehiculeEntity =new ArrayList<>();
        for (VehiculeDTO vehiculeDTO :vehiculeDTOList){
            CategorieEntity categorieEntity=   this.categorieRepository.rechercherCategorieByType(vehiculeDTO.getCategorie().getType());
            if(vehiculeDTO.getImmatriculation()!=null && categorieEntity!=null && categorieEntity.getId()!=null) {
                VehiculeEntity vehicule= this.vehiculeRepository.rechercherVehiculeByMatriculeAndIdTransporteurAndIdCategorie(vehiculeDTO.getImmatriculation(),categorieEntity.getId());
                if(vehicule!=null) {
                    log.info("matricule {} exist ",vehicule.getImmatriculation());
                    vehicule.setPoidsVide(vehiculeDTO.getPoidsVide());
                    vehicule.setDateModification(new Date());
                    listVehiculeEntity.add(vehicule);
                }else {
                    VehiculeEntity vehiculeNew= this.vehiculeConverter.transform(vehiculeDTO);
                    vehiculeNew.setCategorieEntity(categorieEntity);
                    listVehiculeEntity.add(vehiculeNew);
                }
            }else{
                log.error("Categorie {} - matricule {} n'exist pas ",vehiculeDTO.getCategorie().getType(),vehiculeDTO.getImmatriculation());
            }
        }
        return listVehiculeEntity;
    }
}
