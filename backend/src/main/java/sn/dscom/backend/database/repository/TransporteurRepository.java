package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.TransporteurEntity;

import java.time.LocalDateTime;

@Repository
public interface TransporteurRepository extends JpaRepository<TransporteurEntity,Long> {
    @Query(value = "select count(c) from TransporteurEntity c")
    Integer compterTransporteurPardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);
}
