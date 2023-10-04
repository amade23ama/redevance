package sn.dscom.backend.service.util;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.entite.ProfilEntity;
import sn.dscom.backend.database.entite.UtilisateurEntity;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurSpecifications {
    public static Specification<UtilisateurEntity> withUtilisateurIdsAndProfilsIds(List<Long> idUtilisateurs,
                                                                                   List<String> idProfils) {
            return (root, query, criteriaBuilder) -> {

                List<Predicate> predicates = new ArrayList<>();
                if (idUtilisateurs != null && !idUtilisateurs.isEmpty()) {
                    query.distinct(true);
                    Predicate condition = criteriaBuilder.in(root.get("id")).value(idUtilisateurs);
                    predicates.add(condition);
                }
                if (idProfils != null && !idProfils.isEmpty()) {
                    query.distinct(true);
                    Join<UtilisateurEntity, ProfilEntity> profilJoin = root.join("profils");
                    Predicate conditionProfil = profilJoin.get("code").in(idProfils);
                    predicates.add(conditionProfil);
                }
                if (!predicates.isEmpty()) {
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                } else {
                    return criteriaBuilder.conjunction();
                }
            };

    }
}
