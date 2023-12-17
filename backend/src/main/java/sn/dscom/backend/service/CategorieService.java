package sn.dscom.backend.service;

import com.google.common.base.Strings;
import io.vavr.control.Try;
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
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.controller.DepotController;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.entite.VehiculeEntity;
import sn.dscom.backend.database.repository.CategorieRepository;
import sn.dscom.backend.service.converter.CategorieConverter;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;
import sn.dscom.backend.service.interfaces.ICategorieService;
import sn.dscom.backend.service.util.CategorieSpecifications;
import sn.dscom.backend.service.util.VehiculeSpecifications;

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
    private static final Logger log = LoggerFactory.getLogger(DepotController.class);

    /** categorie Repository */
    private final CategorieRepository categorieRepository;

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

        CategorieService.log.info("Recherche des catégories");
        // On charge l'ensemble des Categories
        List<CategorieEntity> listCategorieFind = this.categorieRepository.findAll();

        //retourne la liste
        return Optional.of(listCategorieFind.stream()
                .map(this.categorieConverter::reverse)
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
            CategorieService.log.info(String.format("Recherche de catégories par id: %s", categorieDTO.getId()));
            Optional<CategorieEntity> categorieEntity = this.categorieRepository.findById(categorieDTO.getId());

            // Si on trouve rien on lève une axception
            if (categorieEntity.isEmpty()){
                CategorieService.log.info(String.format("La Categorie d'id %s n'est pas trouvé en base ", categorieDTO.getId()));
                throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
            }
            return Optional.of(Collections.singletonList(Try.of(categorieEntity::get)
                    .mapTry(this.categorieConverter::reverse)
                    .onFailure(e -> CategorieService.log.error(String.format("Erreur lors du categorieConverter.reverse : %s", e.getMessage())))
                    .get()));

        } else if (!Strings.isNullOrEmpty(categorieDTO.getType())) {
            //Recherche par type
            CategorieService.log.info(String.format("Recherche de catégories par crirère: %s", categorieDTO.getType()));
            CategorieEntity categorieEntity = Try.of(categorieDTO::getType)
                    .mapTry(this.categorieRepository::rechercherCategorieByType)
                    .onFailure(e -> CategorieService.log.error(String.format("Erreur lors de la rechercher Categorie By Type: %s", e.getMessage())))
                    .get();

            // Si on trouve rien on lève une axception
           /* if (null == categorieEntity){
                CategorieService.log.info(String.format("La Categorie de type %s n'est pas trouvé en base ", categorieDTO.getType()));
                throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
            }*/

            return Optional.of(Collections.singletonList(Try.of(() -> categorieEntity)
                    .mapTry(this.categorieConverter::reverse)
                    .onFailure(e -> CategorieService.log.error(String.format("Erreur lors du categorieConverter.reverse : %s", e.getMessage())))
                    .get()));

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

    @Override
    public Page<CategorieDTO> rechargementParCritere(CritereRecherche<?> critereRecherche) {
        Sort sort = Sort.by(Sort.Order.asc("id"));
        PageRequest pageRequest = PageRequest.of(critereRecherche.getPage(), critereRecherche.getSize(),sort);
        //S'il n'y a pas de critère on remonte tout
        if (critereRecherche.getAutocompleteRecherches().size() == 0){
            /** find all de tous les véhicule*/
            Page<CategorieEntity> listCategorieFind = this.categorieRepository.findAll(pageRequest);

            List<CategorieDTO> listCategories = listCategorieFind.getContent().stream()
                    .map(categorieConverter::reverse)
                    .collect(Collectors.toList());

            return new PageImpl<>(listCategories, pageRequest, listCategorieFind.getTotalElements());
        }

        List<Long> idsCategorie = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == CategorieEntity.class)
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());

        List<Long> valueVolumes = new ArrayList<>(critereRecherche.getAutocompleteRecherches().stream()
                .filter(item -> item instanceof AutocompleteRecherche)
                .filter(item -> ((AutocompleteRecherche) item).getTypeClass() == String.class)
                .filter(item -> ((AutocompleteRecherche) item).getOrigine().equals("Volume"))
                .map(item -> Long.parseLong(((AutocompleteRecherche) item).getId().toString()))
                .toList());

        Specification<CategorieEntity> spec = Specification
                .where(CategorieSpecifications.withCategorie(idsCategorie, valueVolumes));

        Page<CategorieEntity> listCategorieFind= this.categorieRepository.findAll(spec, pageRequest);

        List<CategorieDTO> listCategorie = listCategorieFind.stream()
                .map(this.categorieConverter::reverse)
                .filter(Objects::nonNull)
                .toList();

        return new PageImpl<>(listCategorie, pageRequest, listCategorieFind.getTotalElements());
    }

    @Override
    public CategorieDTO chargerCategorieDTOParId(Long id) {
            Optional<CategorieEntity> categorie = categorieRepository.findById(id);
            if (categorie.isPresent()) {
                return this.categorieConverter.reverse(categorie.get());
            } else {
                throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
            }

    }

    @Override
    public CategorieDTO rechercheCategorieByType(String type) {
        CategorieEntity categorie = categorieRepository.rechercherCategorieByType(type);
        if (categorie!=null) {
            return this.categorieConverter.reverse(categorie);
        } else {
            log.error("Le Type de Categorie "+type+" n'exist pas");
            return null;
        }
    }
}
