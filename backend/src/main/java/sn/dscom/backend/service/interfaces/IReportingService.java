package sn.dscom.backend.service.interfaces;

import org.springframework.http.ResponseEntity;
import sn.dscom.backend.common.dto.BilanDTO;
import sn.dscom.backend.common.dto.ReportingDTO;

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
    List<String> getListeAnnees();

    /**
     * getChargementsAnnuel
     * @param dateDebutAnnee dateDebutAnnee
     * @param dateFinAnnee dateFinAnnee
     * @return liste
     */
    BilanDTO getChargementsAnnuel(Date dateDebutAnnee, Date dateFinAnnee);
}
