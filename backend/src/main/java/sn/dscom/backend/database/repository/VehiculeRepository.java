package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.VehiculeEntity;

/**
 * Vehicule Repository
 */
@Repository
public interface VehiculeRepository extends JpaRepository<VehiculeEntity,Long> {
}
