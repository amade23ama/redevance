package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.ReportingDTO;

import java.util.Date;
import java.util.List;

/**
 * IReportingService: fournisseur du tableau de bord
 * @author diome
 */
public interface IReportingService {

    List<ReportingDTO> rechercherReportingChargementByRegion(Date dateDebut, Date dateFin);

    List<ReportingDTO> getRecouvrementProduitParAnne();

    List<ReportingDTO> reportingProduitByYear(Date dateDebut, Date dateFin);
}
