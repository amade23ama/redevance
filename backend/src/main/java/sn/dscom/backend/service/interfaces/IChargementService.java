package sn.dscom.backend.service.interfaces;

import org.springframework.data.domain.Page;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;

import java.io.UnsupportedEncodingException;
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
    void effectuerChargement(List<String> ligneChargement, Map<String, String> mapCorrespondance, List<String> header, DepotDTO depotDTO,
                             ProduitDTO produitDTO, ExploitationDTO exploitationDTO, CategorieDTO categorieDTO, SiteDTO siteDTO) throws DscomTechnicalException;

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
    List<Integer> getListeAnnee();

    /**
     *
     * @param dateDebutAnnee dateDebutAnnee
     * @param dateFinAnnee dateDebutAnnee
     * @return liste
     */
    Integer getChargementsAnnuel(Date dateDebutAnnee, Date dateFinAnnee);

    /**
     * rechargementParCritere
     * @param critereRecherche critereRecherche
     * @return Page<ChargementDTO>
     */
    Page<ChargementDTO> rechargementParCritere(CritereRecherche<?> critereRecherche);

    /**
     * rechargementParCritere
     * @param critereRecherche critereRecherche
     * @return Page<ChargementDTO>
     */
    List<ChargementDTO> rechercherChargementParCritere(CritereRecherche<?> critereRecherche);

    /**
     * chargementDTOs To Bytes
     * chargementDTOsToBytes
     * @param datas le liste
     * @return le fichier en byte
     */
    byte[] chargementDTOsToBytes(List<ChargementDTO> datas) throws UnsupportedEncodingException;

    /**
     * chargerChargementParId
     * @param id id
     * @return ChargementDTO
     */
    ChargementDTO chargerChargementParId(Long id);

    /**
     * modifierChargement
     * @param chargementDTO chargementDTO
     * @return ChargementDTO
     */
    ChargementDTO modifierChargement(ChargementDTO chargementDTO);

    /**
     * supprimerChargement Par Id
     * @param chargementDTO chargementDTO
     * @return true or false
     */
    Boolean supprimerChargementParId(List<ChargementDTO> chargementDTO);

    /**
     * supprimerChargementBycritere
     * @param critereRecherche critereRecherche
     * @return  true or false
     */
    Boolean supprimerChargementBycritere(CritereRecherche critereRecherche);

    ChargementDTO genereLineChargement(VehiculeDTO vehiculeDTO, SiteDTO siteDTO, ExploitationDTO exploitationDTO, ProduitDTO produitDTO, String destination, String poidsMesure, String poidsMax, String date, String heure);
    ChargementDTO recherChargementByEntity(ChargementDTO chargementDTO);
    ChargementDTO miseAjourChargement(ChargementDTO chargementDTO);
}
