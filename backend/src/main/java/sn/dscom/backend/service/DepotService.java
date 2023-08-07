package sn.dscom.backend.service;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.database.repository.DepotRepository;
import sn.dscom.backend.service.converter.DepotConverter;
import sn.dscom.backend.service.interfaces.IDepotService;
import sn.dscom.backend.service.interfaces.IUtilisateurService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * interface du service Depot
 */
@Transactional
public class DepotService implements IDepotService {

    /**
     * depot Repository
     */
    private DepotRepository depotRepository;

    /**
     * explitation Converteur
     */
    private final Transformer<DepotDTO, DepotEntity> depotConverteur = new DepotConverter();

    /**
     * DepotService
     * @param depotRepository
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
                .map(depoEntity -> this.depotConverteur.reverse(depoEntity))
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
        if (!depotEntity.isPresent()) {
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
        try {
            Optional<DepotEntity> exploitationEntity = depotRepository.findById(id);
            if (exploitationEntity.isPresent()) {
                return Optional.of(this.depotConverteur.reverse(exploitationEntity.get()));
            }
            // retourner un message d'erreur édéquat: ici "le site avec {id} n'existe pas -> 404"
            // ce n'est pas une erreur inattendue: ErreurEnum.ERR_INATTENDUE
            throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_INATTENDUE);
        } catch (CommonMetierException e) {
            //todo
            throw e;

        }
    }
}
