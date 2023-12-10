package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.ReferenceAnneeRepository;
import sn.dscom.backend.database.repository.SiteRepository;
import sn.dscom.backend.service.ReferenceAnneeService;
import sn.dscom.backend.service.SiteService;

@Configuration
public class ReferenceAnneeServiceConfig {
    @Bean
    public ReferenceAnneeService ReferenceAnneeService(ReferenceAnneeRepository referenceAnneeRepository) {

        return ReferenceAnneeService.builder()
                .referenceAnneeRepository(referenceAnneeRepository)
                .build();
    }
}
