package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.ProduitEntity;

/**
 * Produit Repository
 */
@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity,Long>{
    /**
     *
     * @return
     */
    @Query("SELECT max(id) FROM ProduitEntity")
    Long getMaxId();

    /**
     * recherche produit
     * @param nomSRC nomSRC
     * @param nomNORM nomSRC
     * @return le ou les produits trouves
     */
    @Query("SELECT produit FROM ProduitEntity produit where produit.nomSRC =:nomSRC or produit.nomNORM =:nomNORM")
    ProduitEntity rechercherProduitByCriteres(@Param("nomSRC") String nomSRC, @Param("nomNORM") String nomNORM);
}
