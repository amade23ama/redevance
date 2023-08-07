package sn.dscom.backend.service;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.repository.CategorieRepository;
import sn.dscom.backend.service.converter.CategorieConverter;
import sn.dscom.backend.service.interfaces.ICategorieService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @apiNote implémentation des operations sur les Catégorie de véhicule
 *
 * @version 1
 */
@Transactional
public class CategorieService implements ICategorieService {

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
    public Optional<CategorieDTO> enregistrerCategorie(CategorieDTO categorieDTO) {

        // Vérifiacation
        CategorieEntity categorieEntity = this.categorieRepository.isCategorieExist(categorieDTO.getType().toUpperCase(), categorieDTO.getVolume());

        // s'il existe on renvoit le site existant
        if(categorieEntity != null && categorieDTO.getId() == null ){
            return Optional.of(this.categorieConverter.reverse(categorieEntity));
        }
        //C'est la séquence qui génère l'id en cas de création
        return Optional.of(
                this.categorieConverter.reverse(this.categorieRepository.save(this.categorieConverter.transform(categorieDTO)))
        );
    }

    /**
     * Retourne l'ensemble des sites d'Exploitation
     *
     * @return la liste des sites
     */
    @Override
    public Optional<List<CategorieDTO>> rechercherCategories() {

        // On charge l'ensemble des Categories
        List<CategorieEntity> listCategorieFind = this.categorieRepository.findAll();

        //retourne la liste
        return Optional.of(listCategorieFind.stream()
                .map(categorieEntity -> this.categorieConverter.reverse(categorieEntity))
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

        //recherche par id
        if (categorieDTO.getId() != null) {
            return Optional.of(Arrays.asList(this.categorieConverter.reverse(this.categorieRepository.findById(categorieDTO.getId()).get())));
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
            // on supprime par id
            this.categorieRepository.deleteById(categorieDTO.getId());
            return true;
        }catch (Exception e){
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
