package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.common.dto.BilanDTO;
import sn.dscom.backend.database.entite.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChargementRepository extends JpaRepository<ChargementEntity,Long>{

    @Query(value = "select count(c) from ChargementEntity c")
    Integer compterChargementPardate(@Param("dateMiseEnService") LocalDateTime dateMiseEnService);

    @Query(value = "select c from ChargementEntity c where c.siteEntity=:siteEntity AND c.produitEntity=:produitEntity AND c.vehiculeEntity=:vehiculeEntity AND c.destination=:destination AND c.poids=:poidMax AND c.poidsMax=:poidMesure")
    Optional<ChargementEntity> isChargementExist(@Param("siteEntity") SiteEntity siteEntity, @Param("produitEntity") ProduitEntity produitEntity, @Param("vehiculeEntity") VehiculeEntity vehiculeEntity, @Param("destination") String destination, @Param("poidMax") Double poidMax, @Param("poidMesure") Double poidMesure);

    /**
     * find Chargement By Destination
     * @param destination destination
     * @return la liste
     */
    //@Query(value = "select c from ChargementEntity c where c.destination=:destination")
    Optional<List<ChargementEntity>> findChargementEntitiesByDestination(@Param("destination") String destination);

    /**
     * find Chargement Entities By Exploitation Entity : Origine
     * @param exploitationEntity exploitationEntity
     * @return la liste
     */
    //@Query(value = "select c from ChargementEntity c where c.exploitationEntity=:exploitationEntity")
    Optional<List<ChargementEntity>> findChargementEntitiesByExploitationEntity(@Param("exploitationEntity") ExploitationEntity exploitationEntity);

    /**
     * find Chargement Entities By SiteEntity : Site de pesage
     * @param siteEntity siteEntity
     * @return la liste
     */
    Optional<List<ChargementEntity>> findChargementEntitiesBySiteEntity(@Param("siteEntity") SiteEntity siteEntity);

    /**
     * find Chargement Entities By DatePassageAfter
     * @param datePesage datePesage
     * @return la liste
     */
    Optional<List<ChargementEntity>> findChargementEntitiesByDatePassageAfter(@Param("datePesage") Date datePesage);

    /**
     * findChargementEntitiesByDatePassageAfterAndDestination
     * @param datePesage datePesage
     * @param destination destination
     * @return la liste
     */
    Optional<List<ChargementEntity>> findChargementEntitiesByDatePassageAfterAndDestination(@Param("datePesage") Date datePesage, @Param("destination") String destination);

    /**
     * quantiteParRegionParAn
     * @param listExploitationEntity ListExploitationEntity
     * @return  la liste
     */
    @Query(value = "SELECT sum(c.poidsSubsitance) FROM ChargementEntity c WHERE c.exploitationEntity in (:listExploitationEntity) and c.datePassage>:dateDebut and c.datePassage<:dateFin")
    Double quantiteParRegionParAn(@Param("listExploitationEntity") List<ExploitationEntity> listExploitationEntity, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    /**
     * quantiteParRegionParAn
     * @param produit produit
     * @return  la liste
     */
    @Query(value = "SELECT sum(c.poidsSubsitance) FROM ChargementEntity c WHERE c.produitEntity=:produit and c.datePassage>:dateDebut and c.datePassage<:dateFin")
    Double quantiteByProductByYear(@Param("produit") ProduitEntity produit, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    /**
     * quantite Recouvrement Annuel
     * @param listProduitEntity listProduitEntity
     * @param dateDebut dateDebut
     * @param dateFin dateFin
     * @return liste
     */
    @Query(value = "SELECT sum(c.poidsSubsitance) FROM ChargementEntity c WHERE c.produitEntity in (:listProduitEntity) and c.datePassage>:dateDebut and c.datePassage<:dateFin")
    Double quantiteRecouvrementAnnuel(@Param("listProduitEntity") List<ProduitEntity> listProduitEntity, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    /**
     * getListeAnnee
     * @return liste année
     */
    @Query(value = "SELECT DATE_PART('YEAR', c.datePassage) AS published_year FROM ChargementEntity c GROUP BY DATE_PART('YEAR', c.datePassage)")
    List<Integer> getListeAnnee();

    /**
     * countChargementAnnuel
     * @param dateDebut dateDebut
     * @param dateFin dateFin
     * @return le nombre de chargement annuel
     */
    @Query(value = "select count(c) from ChargementEntity c where c.datePassage>:dateDebut and c.datePassage<:dateFin")
    Integer countChargementAnnuel(@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    /**
     *
     * @param spec
     * @return
     */
    List<ChargementEntity> findAll(Specification<ChargementEntity> spec);
}
