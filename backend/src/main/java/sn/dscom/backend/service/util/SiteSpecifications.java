package sn.dscom.backend.service.util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import sn.dscom.backend.database.entite.ExploitationEntity;
import sn.dscom.backend.database.entite.SiteEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SiteSpecifications {
    public static Specification<SiteEntity> withSite(List<Long> siteIds, List<String> localites) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (siteIds != null && !siteIds.isEmpty()) {
                query.distinct(true);
                Predicate condition = criteriaBuilder.in(root.get("id")).value(siteIds);
                predicates.add(condition);
            }
            if (localites != null && !localites.isEmpty()) {
                query.distinct(true);
                List<Predicate> localitePredicates = localites.stream()
                        .map(value -> criteriaBuilder.like(
                                criteriaBuilder.upper(root.get("localite")),"%" + value.toUpperCase() + "%"))
                        .collect(Collectors.toList());
                if(!localitePredicates.isEmpty()){
                    predicates.add(criteriaBuilder.or(localitePredicates.toArray(new Predicate[0])));
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
