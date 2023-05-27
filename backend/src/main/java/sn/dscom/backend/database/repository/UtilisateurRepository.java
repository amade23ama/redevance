package sn.dscom.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.dscom.backend.database.entite.UtilisateurEntity;

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
}
