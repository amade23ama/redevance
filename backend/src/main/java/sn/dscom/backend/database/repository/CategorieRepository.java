package sn.dscom.backend.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.VehiculeEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Categorie Repository
 */
@Repository
public interface CategorieRepository extends JpaRepository<CategorieEntity,Long> {

    /**
     * nombre de ligne
     * @param dateMiseEnService dateMiseEnService
     * @return nb site
     */
    @Query(value = "select count(c) from CategorieEntity c")
    Integer compterSitePardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);

    /**
     * is Site Exist
     * @param type type
     * @param volume volume
     * @return TransporteurEntity
     */
    @Query(value = "select categorie from CategorieEntity categorie where categorie.type =:type and  categorie.volume=:volume")
    CategorieEntity isCategorieExist(@Param("type") String type, @Param("volume") double volume);

    /**
     * rechercher Categorie By Type
     *
     * @param type la classe
     * @return CategorieEntity
     */
    @Query(value = "select categorie from CategorieEntity categorie where categorie.type =:type")
    CategorieEntity rechercherCategorieByType(@Param("type") String type);

    List<CategorieEntity> findCategorieEntitiesByIdIsIn(@Param("idsCategorie") List<Long> idsCategorie);
    Page<CategorieEntity> findAll(Specification<CategorieEntity> spec, Pageable pageable);
}
