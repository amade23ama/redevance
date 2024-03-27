package sn.dscom.backend.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 /**
 * @apiNote interface de service site d'exploitation
 * @version 1
 */
public interface ICategorieService {

    /**
     * Permet de modifier ou de creer une categorie
     *
     * @param categorieDTO le site à enregistrer ou à modifier
     * @return le site enregistrer
     */
    Optional<CategorieDTO> enregistrerCategorie(CategorieDTO categorieDTO) throws DscomTechnicalException;

    /**
     * Retourne l'ensemble des sites d'Exploitation
     * @return la liste des sites
     */
    Optional<List<CategorieDTO>> rechercherCategories();

    /**
     * Permet de faire une recherche spécifique: exemple id
     *
     * @param categorieDTO categorieDTO
     * @return la liste trouvée
     */
    Optional<List<CategorieDTO>> rechercherCategorie(CategorieDTO categorieDTO);

    /**
     * Permet de supprimer une categorie
     *
     * @param categorieDTO categorieDTO
     * @return true si supprimé
     */
    Boolean supprimerCategorie(CategorieDTO categorieDTO);

    /**
     * Permet de comter le nombre de Catégorie
     *
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre de site d'Exploitation
     */
    Integer compterCategorie(LocalDateTime dateMiseEnService);

    /**
     * rechargementParCritere
     * @param critereRecherche critereRecherche
     * @return Page<CategorieDTO>
     */
    Page<CategorieDTO> rechargementParCritere(CritereRecherche<?> critereRecherche);

    /**
     * chargerCategorieDTOParId
     * @param id id
     * @return CategorieDTO
     */
    public CategorieDTO chargerCategorieDTOParId(Long id) ;
    CategorieDTO rechercheCategorieByType(String type);
}
