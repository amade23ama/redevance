package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;

import java.time.LocalDateTime;
import java.util.Date;
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
     */
    void effectuerChargement(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header, DepotDTO depotDTO) throws DscomTechnicalException;

    /**
     * rechercher Chargements par:
     * -Origine
     * -Destination
     * -Site
     * -Date, l’utilisateur doit pouvoir rechercher:
     * -par année (exemple 2022)
     * -par date exacte (01/01/2022)
     * Il est possible de combiner les filtres sur plusieurs champs pour affiner sa recherche.
     * @param chargementDTO chargementDTO
     * @return liste
     */
    Optional<List<ChargementDTO>> rechercherChargements(ChargementDTO chargementDTO);

    double getQuantiteParRegionParAn(List<ExploitationDTO> listExploitation, Date dateDebut, Date dateFin);

    double getQuantiteProduitParAn(ProduitDTO produit, Date dateDebut, Date dateFin);

    double getRecouvrementProduitParAn(List<ProduitDTO> listProduit, Date dateDebut, Date dateFin);

    /**
     * getListeAnnee
     * @return liste
     */
    List<String> getListeAnnee();

    /**
     *
     * @param dateDebutAnnee dateDebutAnnee
     * @param dateFinAnnee dateDebutAnnee
     * @return liste
     */
    Integer getChargementsAnnuel(Date dateDebutAnnee, Date dateFinAnnee);
}
