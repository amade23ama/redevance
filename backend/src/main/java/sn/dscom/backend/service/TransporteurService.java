package sn.dscom.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.TransporteurEntity;
import sn.dscom.backend.database.repository.TransporteurRepository;
import sn.dscom.backend.service.converter.TransporteurConverter;
import sn.dscom.backend.service.interfaces.ITransporteurService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @apiNote implémentation des operations sur les sites d'exploitation
 * @version 1
 */
@Service
@Transactional
public class TransporteurService implements ITransporteurService {

    /** transporteur Repository */
    @Autowired
    private TransporteurRepository transporteurRepository;

    /** transporteur Converter */
    private final Transformer<TransporteurDTO, TransporteurEntity> transporteurConverter = new TransporteurConverter();

    /**
     * Permet de modifier ou de creer un Transporteur
     *
     * @param transporteurDTO le site à enregistrer ou à modifier
     * @return le site enregistrer
     */
    @Override
    public Optional<TransporteurDTO> enregistrerTransporteur(TransporteurDTO transporteurDTO) {

        // Vérifiacation
        TransporteurEntity transporteurEntity = this.transporteurRepository.isTransporteurExist(transporteurDTO.getNom().toUpperCase(), transporteurDTO.getTelephone().toUpperCase());

        // s'il existe on renvoit le site existant
        if(transporteurEntity != null && transporteurDTO.getId() == null ){
            return Optional.of(this.transporteurConverter.reverse(transporteurEntity));
        }

        //C'est la séquence qui génère l'id en cas de création
        return Optional.of(
                this.transporteurConverter.reverse(this.transporteurRepository.save(this.transporteurConverter.transform(transporteurDTO)))
                );
    }

    /**
     * Retourne l'ensemble des Transporteurs
     *
     * @return la liste des sites
     */
    @Override
    public Optional<List<TransporteurDTO>> rechercherTransporteurs() {

        // On charge l'ensemble des sites
        List<TransporteurEntity> listTranporteurFind = this.transporteurRepository.findAll();

        //retourne la liste
        return Optional.of(listTranporteurFind.stream()
                .map(siteEntity -> this.transporteurConverter.reverse(siteEntity))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    /**
     * Permet de faire une recherche spécifique: exemple id
     *
     * @param transporteurDTO exploitationDTO
     * @return la liste trouvée
     */
    @Override
    public Optional<List<TransporteurDTO>> rechercherTransporteur(TransporteurDTO transporteurDTO) {
        //recherche par id
        if (transporteurDTO.getId() != null) {
            return Optional.of(Arrays.asList(this.transporteurConverter.reverse(this.transporteurRepository.findById(transporteurDTO.getId()).get())));
        }
        //TODO: a implementer pour d'autre recherche
        return Optional.empty();
    }

    /**
     * Permet de supprimer un site
     *
     * @param transporteurDTO transporteurDTO
     * @return true si supprimé
     */
    @Override
    public Boolean supprimerTransporteur(TransporteurDTO transporteurDTO) {

        //Suppression de l'élément
        try {
            // on supprime par id
            this.transporteurRepository.deleteById(transporteurDTO.getId());
            return true;
        }catch (Exception e){
            throw new CommonMetierException(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), ErreurEnum.ERR_INATTENDUE);

        }
    }

    /**
     * Permet de comter le nombre de site en base
     *
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre de site d'Exploitation
     */
    @Override
    public Integer compterTransporteurs(LocalDateTime dateMiseEnService) {
        return transporteurRepository.compterTransporteurPardate(dateMiseEnService);
    }
}
