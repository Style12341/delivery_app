package deso.delivery_app.dto;

import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.Comida;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MenuDTO {
    List<Comida> comidas;
    List<Bebida> bebidas;
}
