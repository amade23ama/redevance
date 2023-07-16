package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.CategorieDTO;

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
    Optional<CategorieDTO> enregistrerCategorie(CategorieDTO categorieDTO);

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
}
