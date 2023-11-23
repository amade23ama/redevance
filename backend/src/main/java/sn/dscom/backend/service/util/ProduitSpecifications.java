package sn.dscom.backend.service.util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import sn.dscom.backend.database.entite.ProduitEntity;

import java.util.ArrayList;
import java.util.List;

public class ProduitSpecifications {

    public static Specification<ProduitEntity> withProduit(List<Long> produitIds) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (produitIds != null && !produitIds.isEmpty()) {
                query.distinct(true);
                Predicate condition = criteriaBuilder.in(root.get("id")).value(produitIds);
                predicates.add(condition);
            }
            if (!predicates.isEmpty()) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
