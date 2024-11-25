package deso.delivery_app.specifications;

import deso.delivery_app.dto.PedidoFilterDTO;
import deso.delivery_app.persistance.models.Pedido;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PedidoSpecification {
    public static Specification<Pedido> fromFilter(PedidoFilterDTO filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.between(root.get("precioAcumulado"), filter.getMinPrice(), filter.getMaxPrice()));
            if (filter.getVendedorId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("vendedor").get("id"), filter.getVendedorId()));
            }
            if (filter.getClienteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cliente").get("id"), filter.getClienteId()));
            }
            if (filter.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
            }
            if (filter.getEstado() != null){
                predicates.add(criteriaBuilder.equal(root.get("estado"), filter.getEstado()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
