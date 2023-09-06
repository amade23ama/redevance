package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.BilanDTO;

import java.util.Date;
import java.util.List;

/**
 * IReportingService: fournisseur du tableau de bord
 * @author diome
 */
public interface IReportingService {

    BilanDTO rechercherReportingChargementByRegion(Date dateDebut, Date dateFin);

    BilanDTO getRecouvrementProduitParAnne();

    BilanDTO reportingProduitByYear(Date dateDebut, Date dateFin);

    /**
     * getListeAnnees
     * @return liste
     */
    List<Integer> getListeAnnees();

    /**
     * getChargementsAnnuel
     * @param dateDebutAnnee dateDebutAnnee
     * @param dateFinAnnee dateFinAnnee
     * @return liste
     */
    BilanDTO getChargementsAnnuel(Date dateDebutAnnee, Date dateFinAnnee);
}
