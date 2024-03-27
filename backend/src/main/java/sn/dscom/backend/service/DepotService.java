package sn.dscom.backend.service;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.AutocompleteRecherche;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.ExploitationDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.database.entite.ExploitationEntity;
import sn.dscom.backend.database.repository.DepotRepository;
import sn.dscom.backend.service.converter.DepotConverter;
import sn.dscom.backend.service.interfaces.IDepotService;
import sn.dscom.backend.service.util.DepotSpecifications;
import sn.dscom.backend.service.util.ExploitationSpecifications;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * interface du service Depot
 */
@Transactional
public class DepotService implements IDepotService {
    /** Logger Factory */
    private static final Logger logger = LoggerFactory.getLogger(DepotService.class);

    /**
     * depot Repository
     */
    private final DepotRepository depotRepository;

    /**
     * explitation Converteur
     */
    private final Transformer<DepotDTO, DepotEntity> depotConverteur = new DepotConverter();

    /**
     * DepotService
     * @param depotRepository depotRepository
     */
    @Builder
    public DepotService(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    /**
     * Permet de modifier ou de creer un depot
     *
     * @param depotDTO le site à enregistrer ou à modifier
     * @return le site enregistrer
     */
    @Override
    public Optional<DepotDTO> enregistrerDepot(DepotDTO depotDTO) {

        //C'est la séquence qui génère l'id en cas de création
        return Optional.of(this.depotConverteur.reverse(this.depotRepository.save(this.depotConverteur.transform(depotDTO))));
    }

    /**
     * Retourne l'ensemble des sites d'Exploitation
     *
     * @return la liste des sites
     */
    @Override
    public Optional<List<DepotDTO>> rechercherDepots() {
        // On charge l'ensemble des site
        List<DepotEntity> listDeposFind = this.depotRepository.findAll();

        //retourne la liste
        return Optional.of(listDeposFind.stream()
                .map(this.depotConverteur::reverse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    /**
     * Permet de faire une recherche spécifique: exemple id
     *
     * @param depotDTO depotDTO
     * @return la liste trouvée
     */
    @Override
    public Optional<List<DepotDTO>> rechercherDepotByCriteres(DepotDTO depotDTO) {
        return Optional.empty();
    }

    /**
     * Permet de supprimer un depot
     *
     * @param depotDTO depotDTO
     * @return true si supprimé
     */
    @Override
    public Boolean supprimerDepot(DepotDTO depotDTO) {
        // todo
        final Optional<DepotEntity> depotEntity = depotRepository.findById(depotDTO.getId());
        if (depotEntity.isEmpty()) {
            throw new CommonMetierException(HttpStatus.NOT_ACCEPTABLE.value(), ErreurEnum.ERR_CONTRAT_NOT_FOUND);

        }
        depotRepository.delete(depotEntity.get());
        //return ResponseEntity.status(HttpStatus.OK).build();
        return true;
    }

    /**
     * Permet de comter le nombre de Depot en base
     *
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre de DepotDTO
     */
    @Override
    public Integer compterDepot(LocalDateTime dateMiseEnService) {
        return depotRepository.compterDepotPardate(dateMiseEnService);
    }

    /**
     * Permet de charger le DepotDTO a partir de son id
     *
     * @param id du depot
     * @return le DepotDTO
     */
    @Override
    public Optional<DepotDTO> rechercherDepotById(long id) {
        Optional<DepotEntity> depotEntity = depotRepository.findById(id);
        if (depotEntity.isPresent()) {
            return Optional.of(this.depotConverteur.reverse(depotEntity.get()));
        }
        throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_INATTENDUE);
    }

    /**
     * rechargement Par Critere
     *
     * @param critereRecherche critereRecherche
     * @return liste
     */
    @Override
    public Page<DepotDTO> rechargementParCritere(CritereRecherche<?> critereRecherche) {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        PageRequest pageRequest = PageRequest.of(critereRecherche.getPage(), critereRecherche.getSize(),sort);
        DepotService.logger.info("Recherche de dépot");
        if (critereRecherche.getAutocompleteRecherches().size() == 0){
            Page<DepotEntity> listDepotsFind = this.depotRepository.findAll(pageRequest);
            // On charge l'ensemble des site
            List<DepotDTO>listSites= listDepotsFind.getContent().stream()
                    .map(this.depotConverteur::reverse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return new PageImpl<>(listSites, pageRequest, listDepotsFind.getTotalElements());
        }

       List<Long> idsDepot = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == DepotEntity.class)
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());

        List<String> valueNom = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == String.class)
                .filter(item -> ((AutocompleteRecherche) item).getOrigine().equals("Nom"))
                .map(item ->((AutocompleteRecherche) item).getId())
                .toList());

        Specification<DepotEntity> spec = Specification.where(DepotSpecifications.withDepot(idsDepot, valueNom));

        Page<DepotEntity> listDepotFind= this.depotRepository.findAll(spec,pageRequest);
        List<DepotDTO> listDepot = listDepotFind.getContent().stream()
                .map(this.depotConverteur::reverse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new PageImpl<>(listDepot, pageRequest, listDepotFind.getTotalElements());
    }
}
