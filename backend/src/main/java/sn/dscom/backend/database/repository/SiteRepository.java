package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;import sn.dscom.backend.database.entite.ProduitEntity;
import sn.dscom.backend.database.entite.SiteEntity;

import java.time.LocalDateTime;

/**
 * Site Repository
 */
@Repository
public interface SiteRepository extends JpaRepository<SiteEntity,Long>{
    /**
     * nombre de ligne
     * @param dateMiseEnService dateMiseEnService
     * @return nb site
     */
    @Query(value = "select count(c) from SiteEntity c")
    Integer compterSitePardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);

    /**
     * is Site Exist
     * @param nom nom
     * @param localite localite
     * @return SiteEntity
     */
    @Query(value = "select site from SiteEntity site where site.nom = :nom and  site.localite=:localite")
    SiteEntity isSiteExist(@Param("nom") String nom, @Param("localite") String localite);

    /**
     * recherche Site
     * @param nom nom
     * @return le ou les Sites trouves
     */
    @Query("SELECT site FROM SiteEntity site where site.nom =:nom")
    SiteEntity rechercherSiteByCriteres(@Param("nom") String nom);
}
