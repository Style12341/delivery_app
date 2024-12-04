package deso.delivery_app.specifications;

import deso.delivery_app.dto.BebidaFilterDTO;
import deso.delivery_app.dto.ComidaFilterDTO;
import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.Comida;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ComidaSpecification extends ItemMenuBaseSpecification<Comida> {
    public Specification<Comida> fromFilter(ItemMenuFilterDTO filter) {
        Boolean soloComidas = filter.getSoloComidas();
        Boolean soloBebida = filter.getSoloBebidas();
        // If the user wants only drinks, return a spec that is always false
        if(!soloComidas && soloBebida){
            //Return spec that is always false
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.literal(1), 0);
        }
        return super.fromFilter(filter);
    }
}
