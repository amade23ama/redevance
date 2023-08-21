package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import sn.dscom.backend.database.repository.ChargementRepository;
import sn.dscom.backend.service.ChargementService;
import sn.dscom.backend.service.interfaces.*;

/**
 * configuration ChargementService
 */
@Configuration
public class ChargementServiceConfig {

    /**
     * le bean chargementService
     *
     * @param chargementRepository
     * @return le bean
     */
    @Bean
    public ChargementService chargementService(ChargementRepository chargementRepository, ISiteService siteService, IExploitationService exploitationService,
                                               IProduitService produitService, IVoitureService voitureService, ITransporteurService transporteurService,
                                               ICategorieService categorieService, Environment environment, IDepotService depotService) {
        return ChargementService.builder()
                .chargementRepository(chargementRepository)
                .siteService(siteService)
                .exploitationService(exploitationService)
                .produitService(produitService)
                .voitureService(voitureService)
                .transporteurService(transporteurService)
                .categorieService(categorieService)
                .environment(environment)
                .depotService(depotService)
                .build();
    }
}
