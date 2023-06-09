package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.SiteEntity;

import java.time.LocalDateTime;

@Repository
public interface SiteRepository extends JpaRepository<SiteEntity,Long>{
    @Query(value = "select count(c) from SiteEntity c")
    Integer compterSitePardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);
}
