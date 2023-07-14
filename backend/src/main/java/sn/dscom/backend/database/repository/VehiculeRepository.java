package sn.dscom.backend.database.repository;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.VehiculeEntity;

import java.util.Date;

/**
 * Vehicule Repository
 */
@Repository
public interface VehiculeRepository extends JpaRepository<VehiculeEntity,Long> {

    /**
     * Supprission
     *
     * @param theId de l'entite
     */
    @Modifying
    @Query(value = "DELETE FROM VehiculeEntity v WHERE v.id =:id")
    void deleteParIdentifiant(@Param("id") Long theId);

    /**
     * Mise à jour de l'immatriculation
     *
     * @param immat immat
     * @param id the id
     * @param dtMaj la date de mise à jour
     */
    @Modifying
    @Query("UPDATE VehiculeEntity v SET v.immatriculation=:immat, v.dateModification=:dtMaj WHERE v.id =:id")
    void miseAjourImmatParId(@Param("immat") String immat, @Param("id") Long id, @Param("dtMaj") Date dtMaj);
}
