package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.SiteEntity;

@Repository
public interface SiteRepository extends JpaRepository<SiteEntity,Long>{
}
