package sn.dscom.backend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.dscom.backend.database.repository.*;
import sn.dscom.backend.service.AutocompleteRechercheService;

/**
 * configuration CategorieService
 * @author diome
 */
@Configuration
public class AutocompleteRechercheServiceConfig {

    /**
     * le bean autocompleteRechercheService
     * @param utilisateurRepository utilisateurRepository
     * @param profilRepository profilRepository
     * @param siteRepository siteRepository
     * @param exploitationRepository exploitationRepository
     * @param vehiculeRepository vehiculeRepository
     * @param produitRepository produitRepository
     * @return bean
     */
    @Bean
    public AutocompleteRechercheService autocompleteRechercheService(UtilisateurRepository utilisateurRepository,
                                                                     SiteRepository siteRepository, VehiculeRepository vehiculeRepository,
                                                                     ExploitationRepository exploitationRepository,
                                                                     ProduitRepository produitRepository,
                                                                     ProfilRepository profilRepository,
                                                                     ChargementRepository chargementRepository,
                                                                     CategorieRepository categorieRepository) {
        return AutocompleteRechercheService.builder()
                .profilRepository(profilRepository)
                .utilisateurRepository(utilisateurRepository)
                .produitRepository(produitRepository)
                .exploitationRepository(exploitationRepository)
                .siteRepository(siteRepository)
                .vehiculeRepository(vehiculeRepository)
                .chargementRepository(chargementRepository)
                .categorieRepository(categorieRepository)
                .build();
    }
}
