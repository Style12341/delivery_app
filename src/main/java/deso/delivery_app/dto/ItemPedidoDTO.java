package deso.delivery_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemPedidoDTO {
    private Long itemMenuId;
    private Integer cantidad;
}
