package sn.dscom.backend.service;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.util.ChargementUtils;
import sn.dscom.backend.controller.ExploitationController;
import sn.dscom.backend.database.repository.ExploitationRepository;
import sn.dscom.backend.service.interfaces.IChargementService;
import sn.dscom.backend.service.interfaces.IExploitationService;
import sn.dscom.backend.service.interfaces.IProduitService;
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

    /**
     * produitService
     */
    private final IProduitService produitService;

    @Builder
    public ReportingService(final IExploitationService exploitationService, final IChargementService chargementService, final IProduitService produitService) {
        this.exploitationService = exploitationService;
        this.chargementService = chargementService;
        this.produitService = produitService;
    }

    /**
     * @return liste
     */
    @Override
    public BilanDTO rechercherReportingChargementByRegion(Date dateDebut, Date dateFin) {
        List<String> listRegion = this.exploitationService.getAllRegion();
        List<CampagneBilanDTO> listCampagne = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDebut);
        listRegion
                .forEach(region -> {
                    List<ExploitationDTO> listExploitation = this.exploitationService.findExploitationEntitiesByRegion(region);



                    listCampagne.add(CampagneBilanDTO.builder()
                                                    .libelle(region)
                                                    .quantite(this.chargementService.getQuantiteParRegionParAn(listExploitation, dateDebut, dateFin)/1000)
                                                    .build());
                });

        return BilanDTO.builder()
                .annee(calendar.get(Calendar.YEAR))
                .description("Production par région")
                .campagnes(listCampagne)
                .build();
    }

    /**
     * @return liste
     */
    @Override
    public BilanDTO getRecouvrementProduitParAnne() {
        List<ProduitDTO> listProduit = this.produitService.rechercherProduits().get();
        List<CampagneBilanDTO> listCampagne = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int anneecourante = calendar.get(Calendar.YEAR);
        // les 5 derniers année
        for (int annee = 0; annee < 5;annee++ ){
            int anneeDeCalcul = anneecourante-annee;
            listCampagne.add(CampagneBilanDTO.builder()
                    .libelle(String.valueOf(anneeDeCalcul))
                    .quantite(this.chargementService.getRecouvrementProduitParAn(listProduit,
                            ChargementUtils.getDateDebutAnnee(String.valueOf(anneeDeCalcul)),
                            ChargementUtils.getDateFinAnnee(String.valueOf(anneeDeCalcul)))/1000)
                    .build());
        }
        return BilanDTO.builder()
                .annee(calendar.get(Calendar.YEAR))
                .description("Volume de substance recouvrée par AN")
                .campagnes(listCampagne)
                .build();
    }

    /**
     * @return list
     */
    @Override
    public BilanDTO reportingProduitByYear(Date dateDebut, Date dateFin) {
        List<ProduitDTO> listProduit = this.produitService.rechercherProduits().get();
        List<CampagneBilanDTO> listCampagne = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDebut);
        listProduit
                .forEach(product -> {

                    listCampagne.add(CampagneBilanDTO.builder()
                            .libelle(product.getNomSRC())
                            .quantite(this.chargementService.getQuantiteProduitParAn(product, dateDebut, dateFin)/1000)
                            .build());
                });
        return BilanDTO.builder()
                .annee(calendar.get(Calendar.YEAR))
                .description("Quantité de substance par année")
                .campagnes(listCampagne)
                .build();
    }
}
