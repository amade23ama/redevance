package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.SiteRepository;
import sn.dscom.backend.service.SiteService;

/**
 * SiteService Config
 */
@Configuration
public class SiteServiceConfig {

    /**
     * SiteService
     * @param siteRepository
     * @return Bean
     */
    @Bean
    public SiteService SiteService(SiteRepository siteRepository) {

        return SiteService.builder()
                .siteRepository(siteRepository)
                .build();
    }
}
