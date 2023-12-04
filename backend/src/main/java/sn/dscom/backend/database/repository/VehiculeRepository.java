package sn.dscom.backend.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.VehiculeEntity;

import java.util.Date;
import java.util.List;

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

    /**
     * is Site Exist
     * @param immatriculation immatriculation
     * @return VehiculeEntity
     */
    @Query(value = "select vehicule from VehiculeEntity vehicule where vehicule.immatriculation = :immatriculation")
    VehiculeEntity isVehiculeExist(@Param("immatriculation") String immatriculation);

    /**
     * findVehiculeEntitiesByIdIsIn
     * @param idsVehicule idsVehicule
     * @return la liste
     */
    List<VehiculeEntity> findVehiculeEntitiesByIdIsIn(@Param("idsVehicule") List<Long> idsVehicule);


    Page<VehiculeEntity> findAll(Specification<VehiculeEntity> spec, Pageable pageable);
    @Query(value = "select vehicule from VehiculeEntity vehicule where vehicule.immatriculation = :matricule " +
            "and vehicule.categorieEntity.id=:idCategorie and vehicule.transporteurEntity.id=:idTransport")
    VehiculeEntity  rechercherVehiculeByMatriculeAndIdTransporteurAndIdCategorie(@Param("matricule") String matricule,
                                                                                 @Param("idTransport")long idTransport,
                                                                                 @Param("idCategorie")long idCategorie);
}
