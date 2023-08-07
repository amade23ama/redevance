package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.ProduitRepository;
import sn.dscom.backend.service.ProduitService;

/**
 * ProduitService Config
 */
@Configuration
public class ProduitServiceConfig {

    /**
     * Produit Service
     *
     * @param produitRepository
     * @return Bean
     */
    @Bean
    public ProduitService ProduitService(ProduitRepository produitRepository) {
        return ProduitService.builder()
                .produitRepository(produitRepository)
                .build();
    }
}
