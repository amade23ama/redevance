package sn.dscom.backend.service;

import io.vavr.control.Try;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.AutocompleteRecherche;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ProduitEntity;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.repository.SiteRepository;
import sn.dscom.backend.service.converter.SiteConverter;
import sn.dscom.backend.service.interfaces.ISiteService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Site Service
 */
@Transactional
public class SiteService implements ISiteService {

    /** Logger Factory */
    private static final Logger logger = LoggerFactory.getLogger(SiteService.class);
    /** site Repository */
    private final SiteRepository siteRepository;

    /** Site Converteur */
    private final Transformer<SiteDTO, SiteEntity> siteConverteur = new SiteConverter();

    /**
     * SiteService
     * @param siteRepository siteRepository
     */
    @Builder
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    /**
     * Permet de modifier ou de creer un site
     *
     * @param siteDTO le site à enregistrer ou à modifier
     * @return le site enregistrer
     */
    @Override
    public Optional<SiteDTO> enregistrerSite(SiteDTO siteDTO) {
        // Vérifiacation
        SiteEntity siteEntity = this.siteRepository.isSiteExist(siteDTO.getNom().toUpperCase(), siteDTO.getLocalite().toUpperCase());

        // s'il existe on renvoit le site existant
        if(siteEntity != null && siteDTO.getId() == null ){
            return Optional.of(this.siteConverteur.reverse(siteEntity));
        }
        //C'est la séquence qui génère l'id en cas de création
        return Optional.of(this.siteConverteur.reverse(this.siteRepository.save(this.siteConverteur.transform(siteDTO))));
    }

    /**
     * Retourne l'ensemble des sites
     *
     * @return la liste des sites
     */
    @Override
    public Optional<List<SiteDTO>> rechercherSites() {

        // On charge l'ensemble des site
        List<SiteEntity> listSitesFind = this.siteRepository.findAll();

        //retourne la liste
        return Optional.of(listSitesFind.stream()
                .map(this.siteConverteur::reverse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    /**
     * Permet de faire une recherche spécifique: exemple id
     *
     * @param siteDTO siteDTO
     * @return la liste trouvée
     */
    @Override
    public Optional<List<SiteDTO>> rechercherSite(SiteDTO siteDTO) {
        //siteDTOList
        List<SiteDTO> siteDTOList = null;
        // Recherche par id
        if (siteDTO.getId() != null) {
            Optional<SiteEntity> siteEntity = this.siteRepository.findById(siteDTO.getId());
            // Si le site n'existe pas
            if(siteEntity.isEmpty()){
                SiteService.logger.info(String.format("Le Site d'id %s n'est pas trouvé en base ", siteDTO.getId()));
                throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
            }

            siteDTOList = Collections.singletonList(Try.of(siteEntity::get)
                                                            .mapTry(this.siteConverteur::reverse)
                                                            .get());

        } else if (siteDTO.getNom() != null) {
            SiteEntity siteEntity = this.siteRepository.rechercherSiteByCriteres(siteDTO.getNom());
            siteDTOList = Collections.singletonList(Try.of(() -> siteEntity)
                                                            .mapTry(this.siteConverteur::reverse)
                                                            .get());
        }
        //TODO: a implementer pour d'autre recherche
        assert siteDTOList != null;
        return Optional.of(siteDTOList);
    }

    /**
     * Permet de supprimer un site
     *
     * @param siteDTO siteDTO
     * @return true si supprimé
     */
    @Override
    public Boolean supprimerSite(SiteDTO siteDTO) {
        try {
            // on supprime par id
            this.siteRepository.deleteById(siteDTO.getId());
            return true;
        }catch (Exception e){
            throw new CommonMetierException(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), ErreurEnum.ERR_INATTENDUE);

        }
    }

    /**
     * Permet de comter le nombre de site en base
     *
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre de site
     */
    @Override
    public Integer compterSite(LocalDateTime dateMiseEnService) {
        return siteRepository.compterSitePardate(dateMiseEnService);
    }

    /**
     * charger SiteDTO Par Id
     * @param id id
     * @return SiteDTO
     */
    @Override
    public SiteDTO chargerSiteDTOParId(Long id) {
        Optional<SiteEntity> site = siteRepository.findById(id);
        if (site.isPresent()) {
            return this.siteConverteur.reverse(site.get());
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
    public List<SiteDTO> rechargementParCritere(CritereRecherche<?> critereRecherche) {
        List<Long> idsSite = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == SiteEntity.class)
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());

        return Try.of(() -> idsSite)
                .filter(Objects::nonNull)
                .mapTry(this.siteRepository::findSiteEntitiesByIdIsIn)
                .get()
                .stream()
                .map(this.siteConverteur::reverse)
                .collect(Collectors.toList());
    }
}
