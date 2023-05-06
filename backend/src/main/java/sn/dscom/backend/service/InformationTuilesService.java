package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.TypeInfoTuileEnum;
import sn.dscom.backend.common.dto.HomeCardDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class InformationTuilesService {
    private DepotService depotService;
    private  ChargementService chargementService;
    private  SiteService siteService;
    private  TransporteurService transporteurService;
    public List<HomeCardDTO> getInfoTuiles() {
        return Arrays.asList(construireTuileDepot(),
                construireTuileChargement(),
                construireTuileSite(),
                construireTuileTransporteur());

    }
    /**
     * Méthode pour construire la tuile en cours opérateur.
     */
    private HomeCardDTO construireTuileDepot() {
        // Récupération des compteurs depuis la BDD.
        final Integer compteurEnCours =depotService.compterDepot(LocalDateTime.now());

        return HomeCardDTO.builder()
                .typeTuile(TypeInfoTuileEnum.DEPOT.getCode())
                .valeur(compteurEnCours)
                .build();
    }
    private HomeCardDTO construireTuileChargement() {
        // Récupération des compteurs depuis la BDD.
        final Integer compteurEnCours =chargementService.compterChargement(LocalDateTime.now());
        return HomeCardDTO.builder()
                .typeTuile(TypeInfoTuileEnum.CHARGEMENT.getCode())
                .valeur(compteurEnCours)
                .build();
    }
    private HomeCardDTO construireTuileSite() {
        // Récupération des compteurs depuis la BDD.
        final Integer compteurEnCours =siteService.compterSite(LocalDateTime.now());
        return HomeCardDTO.builder()
                .typeTuile(TypeInfoTuileEnum.SITE.getCode())
                .valeur(compteurEnCours)
                .build();
    }
    private HomeCardDTO construireTuileTransporteur() {
        // Récupération des compteurs depuis la BDD.
        final Integer compteurEnCours =transporteurService.compterTransporteur(LocalDateTime.now());
        return HomeCardDTO.builder()
                .typeTuile(TypeInfoTuileEnum.TRANSPORTEUR.getCode())
                .valeur(compteurEnCours)
                .build();
    }
}
