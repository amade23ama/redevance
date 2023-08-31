package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.ExploitationRepository;
import sn.dscom.backend.service.ExploitationService;
import sn.dscom.backend.service.ReportingService;
import sn.dscom.backend.service.interfaces.IChargementService;
import sn.dscom.backend.service.interfaces.IExploitationService;

/**
 * ReportingServiceConfig
 */
@Configuration
public class ReportingServiceConfig {

    /**
     * reporting Service
     *
     * @param exploitationService exploitationService
     * @param chargementService chargementService
     * @return Bean
     */
    @Bean
    public ReportingService reportingService(final ExploitationService exploitationService, final IChargementService chargementService) {
        return ReportingService.builder()
                .exploitationService(exploitationService)
                .chargementService(chargementService)
                .build();
    }
}
