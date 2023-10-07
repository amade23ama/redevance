package sn.dscom.backend.service.util;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import sn.dscom.backend.database.entite.VehiculeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehiculeSpecifications {
    public static Specification<VehiculeEntity> withVehiculeIdsAndCategorieIds(List<Long> vehiculeIds,
                                                                         List<Long> categorieIds,
                                                                               List<Long> volumes) {
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
            if (volumes != null && !volumes.isEmpty()) {
                query.distinct(true);
                List<Predicate> volumePredicates = volumes.stream()
                        .map(value -> criteriaBuilder.equal(root.get("categorieEntity").get("volume"),  + value ))
                        .collect(Collectors.toList());
                if(!volumePredicates.isEmpty()){
                    predicates.add(criteriaBuilder.or(volumePredicates.toArray(new Predicate[0])));
                }
            }

            if (!predicates.isEmpty()) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
