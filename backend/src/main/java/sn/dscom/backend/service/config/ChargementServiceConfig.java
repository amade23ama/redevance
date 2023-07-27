package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.ChargementRepository;
import sn.dscom.backend.service.ChargementService;

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
    public ChargementService chargementService(ChargementRepository chargementRepository) {
        return ChargementService.builder()
                .chargementRepository(chargementRepository)
                .build();
    }
}
