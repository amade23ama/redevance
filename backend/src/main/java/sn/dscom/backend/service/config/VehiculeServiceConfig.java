package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.VehiculeRepository;
import sn.dscom.backend.service.VehiculeService;

/**
 * VehiculeService Config
 */
@Configuration
public class VehiculeServiceConfig {

    /**
     * VehiculeService
     *
     * @param vehiculeRepository
     * @return Bean
     */
    @Bean
    public VehiculeService VehiculeService(VehiculeRepository vehiculeRepository) {

        return VehiculeService.builder()
                .vehiculeRepository(vehiculeRepository)
                .build();
    }
}
