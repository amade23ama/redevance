package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.ProfilEntity;
@Repository
public interface ProfilRepository extends JpaRepository<ProfilEntity,String> {
}
