package deso.delivery_app.specifications;

import deso.delivery_app.dto.BebidaFilterDTO;
import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.Vendedor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BebidaSpecification extends ItemMenuBaseSpecification<Bebida> {
    public Specification<Bebida> fromFilter(ItemMenuFilterDTO filter) {
        Specification<Bebida> spec = super.fromFilter(filter);
        BebidaFilterDTO filterBebida = filter.getBebida();
        Boolean alcoholica = filterBebida.getAlcoholica();
        Boolean gaseosa = filterBebida.getGaseosa();
        Double volumenMinimo = filterBebida.getVolumenMinimo();
        Double volumenMaximo = filterBebida.getVolumenMaximo();
        Double graduacionAlcoholicaMinima = filterBebida.getGraduacionAlcoholicaMinima();
        Double graduacionAlcoholicaMaxima = filterBebida.getGraduacionAlcoholicaMaxima();
        Boolean soloBebida = filter.getSoloBebidas();
        Boolean soloComida = filter.getSoloComidas();
        // If the user wants only foods, return a spec that is always false
        if(!soloBebida && soloComida){
            //Return spec that is always false
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.literal(1), 0);
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (alcoholica != null) {
                predicates.add(criteriaBuilder.equal(root.get("esAlcoholica"), alcoholica));
            }
            if (gaseosa != null) {
                predicates.add(criteriaBuilder.equal(root.get("esGaseosa"), gaseosa));
            }
            predicates.add(criteriaBuilder.between(root.get("volumen"), volumenMinimo, volumenMaximo));
            predicates.add(criteriaBuilder.between(root.get("graduacionAlcoholica"), graduacionAlcoholicaMinima, graduacionAlcoholicaMaxima));
            predicates.add(spec.toPredicate(root, query, criteriaBuilder));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
