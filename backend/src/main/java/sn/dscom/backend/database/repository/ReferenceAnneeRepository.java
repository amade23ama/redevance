package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.ReferenceAnneeEntity;
import sn.dscom.backend.database.entite.TransporteurEntity;
@Repository
public interface ReferenceAnneeRepository extends JpaRepository<ReferenceAnneeEntity,Long> {
}
