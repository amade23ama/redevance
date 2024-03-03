package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.ErreurDepotRepository;
import sn.dscom.backend.service.ErreurDepotService;

@Configuration
public class ErreurDepotServiceConfig {

    @Bean
    public ErreurDepotService ErreurDepotService(ErreurDepotRepository erreurDepotRepository) {
        return ErreurDepotService.builder()
                .erreurDepotRepository(erreurDepotRepository)
                .build();
    }
}
