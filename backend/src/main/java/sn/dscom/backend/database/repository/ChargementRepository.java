package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.entite.ProduitEntity;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.entite.VehiculeEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ChargementRepository extends JpaRepository<ChargementEntity,Long>{

    @Query(value = "select count(c) from ChargementEntity c")
    Integer compterChargementPardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);

    @Query(value = "select c from ChargementEntity c where c.siteEntity=:siteEntity AND c.produitEntity=:produitEntity AND c.vehiculeEntity=:vehiculeEntity AND c.destination=:destination AND c.poids=:poidMax AND c.poidsMax=:poidMesure")
    Optional<ChargementEntity> isChargementExist(@Param("siteEntity") SiteEntity siteEntity, @Param("produitEntity") ProduitEntity produitEntity, @Param("vehiculeEntity") VehiculeEntity vehiculeEntity, @Param("destination") String destination, @Param("poidMax") Double poidMax, @Param("poidMesure") Double poidMesure);
}
