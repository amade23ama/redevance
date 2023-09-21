package sn.dscom.backend.service.util;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import sn.dscom.backend.database.entite.ChargementEntity;

import java.util.ArrayList;
import java.util.List;

public class ChargementSpecifications {

    public static Specification<ChargementEntity> withSiteIdsAndProduitIds(List<Long> siteIds,
                                                                           List<Long> produitIds,
                                                                           List<Long> siteExploitIds,
                                                                           List<Long> vehiculeIds) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (siteIds != null && !siteIds.isEmpty()) {
                query.distinct(true);
                root.join("siteEntity", JoinType.INNER);
                Predicate condition = criteriaBuilder.in(root.get("siteEntity").get("id")).value(siteIds);
                predicates.add(condition);
            }
            if (produitIds != null && !produitIds.isEmpty()) {
                query.distinct(true);
                root.join("produitEntity");
                Predicate conditionProduit = criteriaBuilder.in(root.get("produitEntity").get("id")).value(produitIds);
                predicates.add(conditionProduit);
            }
            if (siteExploitIds != null && !siteExploitIds.isEmpty()) {
                query.distinct(true);
                root.join("exploitationEntity");
                Predicate conditionExploitation = criteriaBuilder.in(root.get("exploitationEntity").get("id")).value(siteExploitIds);
                predicates.add(conditionExploitation);
            }
            if (vehiculeIds != null && !vehiculeIds.isEmpty()) {
                query.distinct(true);
                root.join("vehiculeEntity");
                Predicate conditionVehicule = criteriaBuilder.in(root.get("vehiculeEntity").get("id")).value(vehiculeIds);
                predicates.add(conditionVehicule);
            }
            if (!predicates.isEmpty()) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
