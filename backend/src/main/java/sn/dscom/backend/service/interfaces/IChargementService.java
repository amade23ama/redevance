package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.common.dto.DepotDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface Service chargement
 */
public interface IChargementService {

    /**
     * rechercher Chargements
     *
     * @return la liste des Chargements
     */
    Optional<List<ChargementDTO>> rechercherChargements();

    /**
     * enregistrer Chargement
     *
     * @param chargementDTO le Chargement à enregistrer
     * @return le Chargement
     */
    Optional<ChargementDTO> enregistrerChargement(ChargementDTO chargementDTO);

    /**
     * compteur de Chargements
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre
     */
    Integer compterChargement(LocalDateTime dateMiseEnService);

    /**
     * effectuer Chargement
     * @param ligneChargement ligneChargement
     * @param mapCorrespondance mapCorrespondance
     * @param header header
     * @param depotDTO depotDTO
     * @return ChargementDTO
     */
    ChargementDTO effectuerChargement(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header, DepotDTO depotDTO);
}
