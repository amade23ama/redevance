package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.CategorieRepository;
import sn.dscom.backend.database.repository.TransporteurRepository;
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
     * @param vehiculeRepository vehiculeRepository
     * @param transporteurRepository transporteurRepository
     * @return Bean
     */
    @Bean
    public VehiculeService VehiculeService(VehiculeRepository vehiculeRepository, TransporteurRepository transporteurRepository,
                                           CategorieRepository categorieRepository) {

        return VehiculeService.builder()
                .vehiculeRepository(vehiculeRepository)
                .transporteurRepository(transporteurRepository)
                .categorieRepository(categorieRepository)
                .build();
    }
}
