package sn.dscom.backend.service;

import com.google.common.base.Strings;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.controller.DepotController;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.repository.CategorieRepository;
import sn.dscom.backend.service.converter.CategorieConverter;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;
import sn.dscom.backend.service.interfaces.ICategorieService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @apiNote implémentation des operations sur les Catégorie de véhicule
 *
 * @version 1
 */
@Transactional
public class CategorieService implements ICategorieService {

    /** Logger Factory */
    static Logger log = LoggerFactory.getLogger(DepotController.class);

    /** categorie Repository */
    private CategorieRepository categorieRepository;

    /** categorie Converter */
    private final Transformer<CategorieDTO, CategorieEntity> categorieConverter = new CategorieConverter();

    /**
     * CategorieService
     *
     * @param categorieRepository categorieRepository
     */
    @Builder
    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    /**
     * Permet de modifier ou de creer une categorie
     *
     * @param categorieDTO le site à enregistrer ou à modifier
     * @return le site enregistrer
     */
    @Override
    public Optional<CategorieDTO> enregistrerCategorie(CategorieDTO categorieDTO) throws DscomTechnicalException {
            try {
                CategorieService.log.info(String.format("enregistrer Categorie: %s", categorieDTO.getType()));

                // Vérifiacation
                CategorieEntity categorieEntity = this.categorieRepository.isCategorieExist(categorieDTO.getType().toUpperCase(), categorieDTO.getVolume());

                // s'il existe on renvoit le site existant
                if (categorieEntity != null && categorieDTO.getId() == null) {
                    CategorieService.log.info(String.format("La Categorie %s existe déjà en base.", categorieEntity.getType()));
                    return Optional.of(this.categorieConverter.reverse(categorieEntity));
                }

                CategorieService.log.info(String.format("Création de la Categorie: %s", categorieDTO.getType()));
                //C'est la séquence qui génère l'id en cas de création
                return Optional.of(
                        this.categorieConverter.reverse(this.categorieRepository.save(this.categorieConverter.transform(categorieDTO)))
                );
            }catch (Exception e){
                CategorieService.log.error("Erreur de l'enregistrement du chargement");
                throw new DscomTechnicalException(String.format("Erreur lors de l'enregistrement de la catégorie", e.getMessage()));
            }

    }

    /**
     * Retourne l'ensemble des sites d'Exploitation
     *
     * @return la liste des sites
     */
    @Override
    public Optional<List<CategorieDTO>> rechercherCategories() {

        CategorieService.log.error("Recherche des catégories");
        // On charge l'ensemble des Categories
        List<CategorieEntity> listCategorieFind = this.categorieRepository.findAll();

        //retourne la liste
        return Optional.of(listCategorieFind.stream()
                .map(categorieEntity -> this.categorieConverter.reverse(categorieEntity))
                //Filtre sur les elements nulls
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    /**
     * Permet de faire une recherche spécifique: exemple id
     *
     * @param categorieDTO categorieDTO
     * @return la liste trouvée
     */
    @Override
    public Optional<List<CategorieDTO>> rechercherCategorie(CategorieDTO categorieDTO) {
        CategorieService.log.info("Recherche de catégories par crirère");
        //recherche par id
        if (categorieDTO.getId() != null) {
            return Optional.of(Collections.singletonList(this.categorieConverter.reverse(this.categorieRepository.findById(categorieDTO.getId()).get())));
        } else if (!Strings.isNullOrEmpty(categorieDTO.getType())) {
            return Optional.of(Collections.singletonList(this.categorieConverter.reverse(this.categorieRepository.rechercherCategorieByType(categorieDTO.getType()))));
        }
        //TODO: a implementer pour d'autre recherche
        return Optional.empty();
    }

    /**
     * Permet de supprimer une categorie
     *
     * @param categorieDTO categorieDTO
     * @return true si supprimé
     */
    @Override
    public Boolean supprimerCategorie(CategorieDTO categorieDTO) {

        //Suppression de l'élément
        try {
            CategorieService.log.error("suppression Categorie ");
            // on supprime par id
            this.categorieRepository.deleteById(categorieDTO.getId());
            return true;
        }catch (Exception e){
            CategorieService.log.info(String.format("Erreur lors de la suppression de la catégorie: %s", categorieDTO), e.getMessage());
            throw new CommonMetierException(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), ErreurEnum.ERR_INATTENDUE);

        }
    }

    /**
     * Permet de comter le nombre de Catégorie
     *
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre de site d'Exploitation
     */
    @Override
    public Integer compterCategorie(LocalDateTime dateMiseEnService) {
        return this.categorieRepository.compterSitePardate(dateMiseEnService);
    }
}
