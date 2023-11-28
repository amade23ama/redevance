package sn.dscom.backend.service.util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.database.entite.ProduitEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepotSpecifications {

    public static Specification<DepotEntity> withDepot(List<Long> depotsIds, List<String> noms) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (depotsIds != null && !depotsIds.isEmpty()) {
                query.distinct(true);
                Predicate condition = criteriaBuilder.in(root.get("id")).value(depotsIds);
                predicates.add(condition);
            }

            if (noms != null && !noms.isEmpty()) {
                query.distinct(true);
                List<Predicate> nomPredicates = noms.stream()
                        .map(value -> criteriaBuilder.like(
                                criteriaBuilder.upper(root.get("nom")),"%" + value.toUpperCase() + "%"))
                        .toList();
                if(!nomPredicates.isEmpty()){
                    predicates.add(criteriaBuilder.or(nomPredicates.toArray(new Predicate[0])));
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
