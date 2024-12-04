// MenuFilterDTO.java
package deso.delivery_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import deso.delivery_app.enums.TIPO_ITEM;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemMenuFilterDTO {
    private Long vendedorId;
    private Double precioMinimo = 0.0;
    private Double precioMaximo = Double.MAX_VALUE;
    private Boolean esAptoCeliaco;
    private Boolean esAptoVegano;
    private String nombre;
    private Boolean soloComidas = false;
    private Boolean soloBebidas = false;
    private Double pesoMinimo = 0.0;
    private Double pesoMaximo = Double.MAX_VALUE;
    ComidaFilterDTO comida;
    BebidaFilterDTO bebida;
}