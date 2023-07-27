package sn.dscom.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.TypeInfoTuileEnum;
import sn.dscom.backend.common.dto.HomeCardDTO;
import sn.dscom.backend.service.interfaces.IChargementService;
import sn.dscom.backend.service.interfaces.IDepotService;
import sn.dscom.backend.service.interfaces.ISiteService;
import sn.dscom.backend.service.interfaces.ITransporteurService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Service de recherche des infos tuils
 */
@Service
@Transactional
@AllArgsConstructor
public class InformationTuilesService {

    /** depot Service */
    @Autowired
    private IDepotService depotService;

    /** chargement Service */
    @Autowired
    private IChargementService chargementService;

    /** site Service */
    @Autowired
    private ISiteService siteService;

    /** transporteur Service */
    @Autowired
    private ITransporteurService transporteurService;

    /**
     * get Info Tuiles
     * @return la liste
     */
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

        return HomeCardDTO.builder()
                .typeTuile(TypeInfoTuileEnum.DEPOT.getCode())
                .valeur(depotService.compterDepot(LocalDateTime.now()))
                .build();
    }

    /**
     * construire Tuile Chargement
     *
     * @return HomeCardDTO
     */
    private HomeCardDTO construireTuileChargement() {

        // Récupération des compteurs depuis la BDD.
        return HomeCardDTO.builder()
                .typeTuile(TypeInfoTuileEnum.CHARGEMENT.getCode())
                .valeur(chargementService.compterChargement(LocalDateTime.now()))
                .build();
    }

    /**
     * construire Tuile Site
     * @return HomeCardDTO
     */
    private HomeCardDTO construireTuileSite() {
        // Récupération des compteurs depuis la BDD.
        return HomeCardDTO.builder()
                .typeTuile(TypeInfoTuileEnum.SITE.getCode())
                .valeur(siteService.compterSite(LocalDateTime.now()))
                .build();
    }

    /**
     * construire Tuile Transporteur
     * @return HomeCardDTO
     */
    private HomeCardDTO construireTuileTransporteur() {

        // Récupération des compteurs depuis la BDD.
        return HomeCardDTO.builder()
                .typeTuile(TypeInfoTuileEnum.TRANSPORTEUR.getCode())
                .valeur(transporteurService.compterTransporteurs(LocalDateTime.now()))
                .build();
    }
}
