package sn.dscom.backend.service.interfaces;
import org.springframework.data.domain.Page;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.ExploitationDTO;
import sn.dscom.backend.common.dto.ProduitDTO;

import java.util.List;
import java.util.Optional;

/**
 * interface du service produit
 */
public interface IProduitService {

    /**
     * rechercher Produits
     *
     * @return la liste des produits
     */
    Optional<List<ProduitDTO>> rechercherProduits();

    /**
     * enregistrer Produit
     *
     * @param produitDTO le produit à enregistrer
     * @return le produit
     */
     Optional<ProduitDTO> enregistrerProduit(ProduitDTO produitDTO);

    /**
     * rechercher Produits
     ** @param produitDTO le produit à rechercher
     * @return le produit
     */
    Optional<ProduitDTO> rechercherProduit(ProduitDTO produitDTO);

    /**
     * compteur de Produits
     * @return le nombre
     */
    Integer compterProduit();

    /**
     * charger Produit ParId
     * @param id id
     * @return ProduitDTO
     */
    ProduitDTO chargerProduitParId(Long id);

    /**
     * getAllProductName
     * @return liste
     */
    List<String> getAllProductName();

    /**
     * rechargement Par Critere
     * @param critereRecherche critereRecherche
     * @return liste
     */
    Page<ProduitDTO> rechargementParCritere(CritereRecherche<?> critereRecherche);

    /**
     * supprimerProduit
     * @param id id
     * @return Boolean
     */
    Boolean supprimerProduit(Long id);
    ProduitDTO rechercherProduitByNom(String nom);
}
