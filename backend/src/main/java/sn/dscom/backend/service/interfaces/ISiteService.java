package sn.dscom.backend.service.interfaces;

import org.springframework.data.domain.Page;
import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.SiteDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @apiNote interface de service site
 * @version 1
 */
public interface ISiteService {

    /**
     * Permet de modifier ou de creer un site
     *
     * @param siteDTO le site à enregistrer ou à modifier
     * @return le site enregistrer
     */
    Optional<SiteDTO> enregistrerSite(SiteDTO siteDTO);

    /**
     * Retourne l'ensemble des sites
     * @return la liste des sites
     */
    Optional<List<SiteDTO>> rechercherSites();

    /**
     * Permet de faire une recherche spécifique: exemple id
     *
     * @param siteDTO
     * @return la liste trouvée
     */
    Optional<List<SiteDTO>> rechercherSite(SiteDTO siteDTO);

    /**
     * Permet de supprimer un site
     * @param siteDTO
     * @return true si supprimé
     */
    Boolean supprimerSite(SiteDTO siteDTO);

    /**
     * Permet de comter le nombre de site en base
     *
     * @param dateMiseEnService
     * @return le nombre de site
     */
    Integer compterSite(LocalDateTime dateMiseEnService);
    SiteDTO chargerSiteDTOParId(Long id);

    /**
     * rechargement Par Critere
     * @param critereRecherche critereRecherche
     * @return liste
     */
    Page<SiteDTO> rechargementParCritere(CritereRecherche<?> critereRecherche);
    SiteDTO rechercherSiteByNom(String nom);
}
