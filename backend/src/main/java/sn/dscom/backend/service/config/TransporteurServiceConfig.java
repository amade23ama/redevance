package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.TransporteurRepository;
import sn.dscom.backend.service.TransporteurService;

/**
 * TransporteurService Config
 */
@Configuration
public class TransporteurServiceConfig {

    /**
     * TransporteurService
     * @param transporteurRepository the bean
     * @return bean
     */
    @Bean
    public TransporteurService TransporteurService(TransporteurRepository transporteurRepository) {

        return TransporteurService.builder()
                .transporteurRepository(transporteurRepository)
                .build();
    }
}
