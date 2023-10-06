package sn.dscom.backend.service.util;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import sn.dscom.backend.database.entite.VehiculeEntity;

import java.util.ArrayList;
import java.util.List;

public class VehiculeSpecifications {
    public static Specification<VehiculeEntity> withVehiculeIdsAndCategorieIds(List<Long> vehiculeIds,
                                                                         List<Long> categorieIds) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (vehiculeIds != null && !vehiculeIds.isEmpty()) {
                query.distinct(true);
                Predicate condition = criteriaBuilder.in(root.get("id")).value(vehiculeIds);
                predicates.add(condition);
            }
            if (categorieIds != null && !categorieIds.isEmpty()) {
                query.distinct(true);
                root.join("categorieEntity");
                Predicate conditionProduit = criteriaBuilder.in(root.get("categorieEntity").get("id")).value(categorieIds);
                predicates.add(conditionProduit);
            }

            if (!predicates.isEmpty()) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
