// MenuFilterDTO.java
package deso.delivery_app.dto;

import deso.delivery_app.enums.TIPO_ITEM;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemMenuFilterDTO {
    private Double precioMinimo;
    private Double precioMaximo;
    private TIPO_ITEM categoria;
    private Boolean esAptoCeliaco;
    private Boolean esAptoVegano;
    ComidaFilterDTO comida;
    BebidaFilterDTO bebida;
}