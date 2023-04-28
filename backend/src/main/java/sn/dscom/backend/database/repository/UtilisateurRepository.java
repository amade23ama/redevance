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

}
