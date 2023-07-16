package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.CategorieEntity;

import java.time.LocalDateTime;

/**
 * Categorie Repository
 */
@Repository
public interface CategorieRepository extends JpaRepository<CategorieEntity,Long> {

    /**
     * nombre de ligne
     * @param dateMiseEnService dateMiseEnService
     * @return nb site
     */
    @Query(value = "select count(c) from CategorieEntity c")
    Integer compterSitePardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);
}
