package sn.dscom.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
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
@Service
@Transactional
public class SiteService implements ISiteService {

    /** site Repository */
    @Autowired
    private SiteRepository siteRepository;

    /** Site Converteur */
    private Transformer<SiteDTO, SiteEntity> siteConverteur = new SiteConverter();

   /* @Transactional
    public SiteDTO enregistrerSite(SiteDTO siteDTO) {
        //todo
        return miseAjourSite(siteDTO);
    }

    private SiteDTO miseAjourSite(SiteDTO siteDTO) {
        //todo
        SiteEntity siteEntity =SiteConverter.toSiteEntity(siteDTO);
        final Optional<SiteEntity> entitySite = siteRepository.findById(siteDTO.getId());
        if(entitySite.isPresent()){
            siteEntity.setDateModification(new Date());
        }else {
            siteEntity.setDateCreation(new Date());
        }
        SiteEntity site =siteRepository.save(siteEntity);
        return  SiteConverter.toSiteDTO(site);
    }*/

    /**
     * Permet de modifier ou de creer un site
     *
     * @param siteDTO le site à enregistrer ou à modifier
     * @return le site enregistrer
     */
    @Override
    public Optional<SiteDTO> enregistrerSite(SiteDTO siteDTO) {
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
                .map(siteEntity -> this.siteConverteur.reverse(siteEntity))
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
        if (siteDTO.getId() != null) {
         return Optional.of(Arrays.asList(this.siteConverteur.reverse(this.siteRepository.findById(siteDTO.getId()).get())));
        }
        //TODO: a implementer pour d'autre recherche
        return Optional.empty();
    }

    /**
     * Permet de supprimer un site
     *
     * @param siteDTO
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
     * @param dateMiseEnService
     * @return le nombre de site
     */
    @Override
    public Integer compterSite(LocalDateTime dateMiseEnService) {
        return siteRepository.compterSitePardate(dateMiseEnService);
    }
}
