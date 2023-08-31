package sn.dscom.backend.service;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.common.dto.ExploitationDTO;
import sn.dscom.backend.common.dto.ReportingDTO;
import sn.dscom.backend.controller.ExploitationController;
import sn.dscom.backend.database.repository.ExploitationRepository;
import sn.dscom.backend.service.interfaces.IChargementService;
import sn.dscom.backend.service.interfaces.IExploitationService;
import sn.dscom.backend.service.interfaces.IReportingService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service de fourniseur de reporting ou de tableau de bord
 * @author diome
 */
public class ReportingService implements IReportingService {

    /** Logger Factory */
    private static final Logger logger = LoggerFactory.getLogger(ReportingService.class);

    /**
     * exploitation Service
     */
    private final IExploitationService exploitationService;

    /**
     * chargement Service
     */
    private final IChargementService chargementService;

    @Builder
    public ReportingService(final IExploitationService exploitationService, final IChargementService chargementService) {
        this.exploitationService = exploitationService;
        this.chargementService = chargementService;
    }

    /**
     * @return
     */
    @Override
    public List<ReportingDTO> rechercherReportingChargementByRegion(Date dateDebut, Date dateFin) {
        List<String> listRegion = this.exploitationService.getAllRegion();
        List<ReportingDTO> reportingDTOList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDebut);
        listRegion
                .forEach(region -> {
                    List<ExploitationDTO> listExploitation = this.exploitationService.findExploitationEntitiesByRegion(region);



                    reportingDTOList.add(ReportingDTO.builder()
                                                    .annee(calendar.get(Calendar.YEAR))
                                                    .type("CHARG-AN-REGION")
                                                    .libelle(region)
                                                    .data(this.chargementService.getQuantiteParRegionParAn(listExploitation, dateDebut, dateFin))
                                                    .build());
                });
        return reportingDTOList;
    }

    /**
     * @return
     */
    @Override
    public List<ReportingDTO> reportingProduitByExploitation() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<ReportingDTO> reportingProduitByYear() {
        return null;
    }
}
