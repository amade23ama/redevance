package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.ErreurDepotEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ErreurDepotRepository extends JpaRepository<ErreurDepotEntity,Long> {
    @Query(value = "select e from ErreurDepotEntity e where e.idDepot=:idDepot")
    List<ErreurDepotEntity>findAllByIdDepot(@Param("idDepot") Long idDepot);
}
