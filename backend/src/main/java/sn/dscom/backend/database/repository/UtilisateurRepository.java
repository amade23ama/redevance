package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.UtilisateurEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity,Long>{
    @Query(value = "select u from UtilisateurEntity u where u.email = :login or  u.login=:login")
    UtilisateurEntity findUtilisateurEntitiesByLoginExists(@Param("login") String login);
    @Query(value = "select count(u) from UtilisateurEntity u where u.login=:login")
    Integer  checkUtilisateurEntitiesByLoginExists(@Param("login") String login);
    @Query(value = "select count(u) from UtilisateurEntity u where u.email=:email and u.id!=:id")
    Integer  checkEmailExists(@Param("email") String email,@Param("id") Long id);
    @Query(value = "select count(u) from UtilisateurEntity u where u.email=:email")
    Integer  checkEmailExists(@Param("email") String email);
    @Query(value = "select count(u) from UtilisateurEntity u where u.email=:login or  u.login=:login")
    Integer  checkEmailLoginExists(@Param("login") String login);

    /**
     * findUtilisateurEntityByEmail
     * @param email email
     * @return UtilisateurEntity
     */
    Optional<UtilisateurEntity> findUtilisateurEntityByEmail(@Param("email") String email);

    Optional<UtilisateurEntity> findUtilisateurEntityByTelephoneEquals(@Param("telephone") String telephone);
    /*@Query("SELECT DISTINCT u " +
            "FROM UtilisateurEntity u " +
            "LEFT JOIN u.profils p " +
            "WHERE " +
            "(COALESCE(:profileIds, NULL) IS NULL OR p.code IN :profileIds) " +
            "AND " +
            "(COALESCE(:userIds, NULL) IS NULL OR u.id IN :userIds)")
    */
    @Query("SELECT DISTINCT u " +
            "FROM UtilisateurEntity u " +
            "LEFT JOIN u.profils p " +
            "WHERE " +
            "(:profileIds IS NULL OR p.code IN :profileIds) " +
            "AND " +
            "(:userIds IS NULL OR u.id IN :userIds)")

    List<UtilisateurEntity> rechargementParCritere(@Param("userIds") List<Long> userIds,
                                                   @Param("profileIds") List<String> profileIds);

}
