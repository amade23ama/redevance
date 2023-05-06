package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.ChargementEntity;

import java.time.LocalDateTime;

@Repository
public interface ChargementRepository extends JpaRepository<ChargementEntity,Long>{

    @Query(value = "select count(c) from ChargementEntity c")
    Integer compterChargementPardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);
}
