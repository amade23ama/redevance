package sn.dscom.backend.service;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sn.dscom.backend.common.constants.Enum.TypeInfoTuileEnum;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.util.ChargementUtils;
import sn.dscom.backend.service.interfaces.IChargementService;
import sn.dscom.backend.service.interfaces.IExploitationService;
import sn.dscom.backend.service.interfaces.IProduitService;
import sn.dscom.backend.service.interfaces.IReportingService;

import java.util.*;

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
        List<CampagneDTO> listCampagne = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDebut);
        listRegion
                .forEach(region -> {
                    List<ExploitationDTO> listExploitation = this.exploitationService.findExploitationEntitiesByRegion(region);



                    listCampagne.add(CampagneDTO.builder()
                                                    .name(region)
                                                    .value(this.chargementService.getQuantiteParRegionParAn(listExploitation, dateDebut, dateFin)/1000)
                                                    .build());
                });

        return BilanDTO.builder()
                .annee(calendar.get(Calendar.YEAR))
                .description("Production par région")
                .typeTuile(TypeInfoTuileEnum.REGION.getCode())
                .campagnes(this.getNotEmptyCampagne(listCampagne))
                .build();
    }

    /**
     * @return liste
     */
    @Override
    public BilanDTO getRecouvrementProduitParAnne() {
        List<ProduitDTO> listProduit = this.produitService.rechercherProduits().get();
        List<CampagneDTO> listCampagne = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int anneecourante = calendar.get(Calendar.YEAR);
        // les 5 derniers année
        for (int annee = 0; annee < 5;annee++ ){
            int anneeDeCalcul = anneecourante-annee;
            listCampagne.add(CampagneDTO.builder()
                    .name(String.valueOf(anneeDeCalcul))
                    .value(this.chargementService.getRecouvrementProduitParAn(listProduit,
                            ChargementUtils.getDateDebutAnnee(String.valueOf(anneeDeCalcul)),
                            ChargementUtils.getDateFinAnnee(String.valueOf(anneeDeCalcul)))/1000)
                    .build());
        }
        return BilanDTO.builder()
                .annee(calendar.get(Calendar.YEAR))
                .description("Volume de substance recouvrée par AN")
                .typeTuile(TypeInfoTuileEnum.ANNUEL.getCode())
                .campagnes(this.getNotEmptyCampagne(listCampagne))
                .build();
    }

    /**
     * @return list
     */
    @Override
    public BilanDTO reportingProduitByYear(Date dateDebut, Date dateFin) {
        List<ProduitDTO> listProduit = this.produitService.rechercherProduits().get();
        List<CampagneDTO> listCampagne = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDebut);
        listProduit
                .forEach(product -> {

                    listCampagne.add(CampagneDTO.builder()
                            .name(product.getNomSRC())
                            .value(this.chargementService.getQuantiteProduitParAn(product, dateDebut, dateFin)/1000)
                            .build());
                });
        return BilanDTO.builder()
                .annee(calendar.get(Calendar.YEAR))
                .description("Quantité de substance par année")
                .typeTuile(TypeInfoTuileEnum.PRODUIT.getCode())
                .campagnes(this.getNotEmptyCampagne(listCampagne))
                .build();
    }

    /**
     * getListeAnnees
     *
     * @return liste
     */
    @Override
    public List<Integer> getListeAnnees() {
        return this.chargementService.getListeAnnee();
    }

    /**
     * getChargementsAnnuel
     *
     * @param dateDebutAnnee dateDebutAnnee
     * @param dateFinAnnee   dateFinAnnee
     * @return liste
     */
    @Override
    public BilanDTO getChargementsAnnuel(Date dateDebutAnnee, Date dateFinAnnee) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDebutAnnee);
        return BilanDTO.builder()
                .annee(calendar.get(Calendar.YEAR))
                .description("Chargement annuel")
                .typeTuile(TypeInfoTuileEnum.CHARGEMENT.getCode())
                .campagnes(Collections.singletonList(CampagneDTO.builder()
                                .name(TypeInfoTuileEnum.CHARGEMENT.getCode())
                                .value(this.chargementService.getChargementsAnnuel(dateDebutAnnee, dateFinAnnee))
                                .build()))
                .build();
    }

    /**
     * getNotEmptyCampagne
     * @param listCampagne listCampagne
     * @return liste
     */
    private List<CampagneDTO> getNotEmptyCampagne(List<CampagneDTO> listCampagne) {

        return listCampagne.stream()
                .filter(campagneDTO -> campagneDTO.getValue() != 0.0)
                .toList();
    }
}
