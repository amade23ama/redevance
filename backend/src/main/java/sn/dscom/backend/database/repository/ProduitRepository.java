package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.ProduitEntity;
@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity,Long>{
    @Query("SELECT max(id) FROM ProduitEntity")
    Long getMaxId();

   //@Query("ALTER SEQ_PRODUIT serial RESTART :val")
    //Long nexvalu();
}
