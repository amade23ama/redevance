package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.DepotRepository;
import sn.dscom.backend.service.DepotService;

/**
 * DepotService Config
 */
@Configuration
public class DepotServiceConfig {

    /**
     * Depot Service
     * @param depotRepository
     * @return Bean
     */
    @Bean
    public DepotService DepotService(DepotRepository depotRepository) {

        return DepotService.builder()
                .depotRepository(depotRepository)
                .build();
    }
}
