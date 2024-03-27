package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.TransporteurEntity;

import java.time.LocalDateTime;

/**
 * Transporteur Repository
 */
@Repository
public interface TransporteurRepository extends JpaRepository<TransporteurEntity,Long> {

    /**
     * compter Transporteur Pardate
     *
     * @param dateMiseEnService dateMiseEnService
     * @return le nombre de transporteur
     */
    @Query(value = "select count(c) from TransporteurEntity c")
    Integer compterTransporteurPardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);

    /**
     * is Site Exist
     * @param nom nom
     * @param telephone telephone
     * @return TransporteurEntity
     */
    @Query(value = "select transpoteur from TransporteurEntity transpoteur where transpoteur.nom = :nom and  transpoteur.telephone=:telephone")
    TransporteurEntity isTransporteurExist(@Param("nom") String nom, @Param("telephone") String telephone);

    @Query(value = "select transpoteur from TransporteurEntity transpoteur where transpoteur.nom = :nom")
    TransporteurEntity rechercheTransporteurByNom(@Param("nom") String nom);
}
