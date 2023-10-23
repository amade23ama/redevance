package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.VehiculeRepository;
import sn.dscom.backend.service.VehiculeService;
import sn.dscom.backend.service.interfaces.ITransporteurService;

/**
 * VehiculeService Config
 */
@Configuration
public class VehiculeServiceConfig {

    /**
     * VehiculeService
     *
     * @param vehiculeRepository vehiculeRepository
     * @param transporteurService transporteurService
     * @return Bean
     */
    @Bean
    public VehiculeService VehiculeService(VehiculeRepository vehiculeRepository, ITransporteurService transporteurService) {

        return VehiculeService.builder()
                .vehiculeRepository(vehiculeRepository)
                .transporteurService(transporteurService)
                .build();
    }
}
