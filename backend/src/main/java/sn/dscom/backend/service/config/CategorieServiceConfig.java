package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.CategorieRepository;
import sn.dscom.backend.service.CategorieService;

/**
 * configuration CategorieService
 */
@Configuration
public class CategorieServiceConfig {

    /**
     * CategorieService
     * @param categorieRepository the repo
     * @return Bean
     */
    @Bean
    public CategorieService CategorieService(CategorieRepository categorieRepository) {

        return CategorieService.builder()
                .categorieRepository(categorieRepository)
                .build();
    }
}
