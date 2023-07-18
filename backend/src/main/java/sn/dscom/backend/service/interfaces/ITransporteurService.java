package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.TransporteurDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @apiNote  interface de service Transporteur
 * @version 1
 */
public interface ITransporteurService {

    /**
     * Permet de modifier ou de creer un Transporteur
     *
     * @param transporteurDTO le site à enregistrer ou à modifier
     * @return le site enregistrer
     */
    Optional<TransporteurDTO> enregistrerTransporteur(TransporteurDTO transporteurDTO);

    /**
     * Retourne l'ensemble des Transporteurs
     * @return la liste des sites
     */
    Optional<List<TransporteurDTO>> rechercherTransporteurs();

    /**
     * Permet de faire une recherche spécifique: exemple id
     *
     * @param transporteurDTO exploitationDTO
     * @return la liste trouvée
     */
    Optional<List<TransporteurDTO>> rechercherTransporteur(TransporteurDTO transporteurDTO);

    /**
     * Permet de supprimer un site
     * @param transporteurDTO transporteurDTO
     * @return true si supprimé
     */
    Boolean supprimerTransporteur(TransporteurDTO transporteurDTO);

    /**
     * Permet de comter le nombre de site en base
     *
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre de site d'Exploitation
     */
    Integer compterTransporteurs(LocalDateTime dateMiseEnService);
}
