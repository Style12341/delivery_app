package deso.delivery_app.specifications;

import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.Comida;
import deso.delivery_app.persistance.models.Vendedor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemMenuBaseSpecification<T> {

    public Specification<T> fromFilter(ItemMenuFilterDTO filter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Long v = filter.getVendedorId();
            Double precioMinimo = filter.getPrecioMinimo();
            Double precioMaximo = filter.getPrecioMaximo();
            Boolean esAptoCeliaco = filter.getEsAptoCeliaco();
            Boolean esAptoVegano = filter.getEsAptoVegano();
            Double pesoMinimo = filter.getPesoMinimo();
            Double pesoMaximo = filter.getPesoMaximo();
            String nombre = filter.getNombre();
            predicates.add(criteriaBuilder.isNull(root.get("deleted_at")));
            if(nombre!=null){
                predicates.add(criteriaBuilder.like(root.get("nombre"), "%"+nombre+"%"));
            }
            predicates.add(criteriaBuilder.between(root.get("peso"), pesoMinimo, pesoMaximo));
            if(v!=null){
                predicates.add(criteriaBuilder.equal(root.get("vendedor").get("id"), v));
            }
            predicates.add(criteriaBuilder.between(root.get("precio"), precioMinimo, precioMaximo));
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
