package deso.delivery_app.dto;

import deso.delivery_app.enums.ESTADO_PEDIDO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoDTO {
    private Long vendedorId;
    private Long clienteId;
    private ESTADO_PEDIDO estado;
}
