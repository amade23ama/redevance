package sn.dscom.backend.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.DepotEntity;

import java.time.LocalDateTime;

@Repository
public interface DepotRepository extends JpaRepository<DepotEntity,Long> {
    @Query(value = "select count(c) from DepotEntity c")
    Integer compterDepotPardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);
    //@Query(value = "select count(c) from DepotEntity c where c.dateHeureDepot >= :dateMiseEnService ")
    //Integer compterDepotPardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);

    Page<DepotEntity> findAll(Specification<DepotEntity> spec, Pageable pageable);
}
