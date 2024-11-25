package deso.delivery_app.specifications;

import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.ItemMenu;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ItemMenuSpecification {
    public static Specification<ItemMenu> fromFilter(
            Double precioMinimo,
            Double precioMaximo,
            Boolean esAptoCeliaco,
            Boolean esAptoVegano) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (precioMinimo != null && precioMaximo != null) {
                predicates.add(criteriaBuilder.between(root.get("precio"), precioMinimo, precioMaximo));
            } else if (precioMinimo != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("precio"), precioMinimo));
            } else if (precioMaximo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precioMaximo));
            }
            if (esAptoCeliaco != null) {
                predicates.add(criteriaBuilder.equal(root.get("esAptoCeliaco"), esAptoCeliaco));
            }
            if (esAptoVegano != null) {
                predicates.add(criteriaBuilder.equal(root.get("esAptoVegano"), esAptoVegano));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
