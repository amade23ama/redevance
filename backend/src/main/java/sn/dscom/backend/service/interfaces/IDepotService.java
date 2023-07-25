package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.DepotDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * interface du service Depot
 */
public interface IDepotService {
    /**
     * Permet de modifier ou de creer un depot
     *
     * @param depotDTO le site à enregistrer ou à modifier
     * @return le site enregistrer
     */
    Optional<DepotDTO> enregistrerDepot(DepotDTO depotDTO);

    /**
     * Retourne l'ensemble des sites d'Exploitation
     * @return la liste des sites
     */
    Optional<List<DepotDTO>> rechercherDepots();

    /**
     * Permet de faire une recherche spécifique: exemple id
     *
     * @param depotDTO depotDTO
     * @return la liste trouvée
     */
    Optional<List<DepotDTO>> rechercherDepotByCriteres(DepotDTO depotDTO);

    /**
     * Permet de supprimer un depot
     * @param depotDTO depotDTO
     * @return true si supprimé
     */
    Boolean supprimerDepot(DepotDTO depotDTO);

    /**
     * Permet de comter le nombre de Depot en base
     *
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre de DepotDTO
     */
    Integer compterDepot(LocalDateTime dateMiseEnService);

    /**
     * Permet de charger le DepotDTO a partir de son id
     * @param id du depot
     * @return le DepotDTO
     */
    Optional<DepotDTO> rechercherDepotById(long id);
}
