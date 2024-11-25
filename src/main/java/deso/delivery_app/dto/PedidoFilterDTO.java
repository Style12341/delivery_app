package deso.delivery_app.dto;

import deso.delivery_app.enums.ESTADO_PEDIDO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PedidoFilterDTO {
    private Long id;
    private ESTADO_PEDIDO estado;
    private Double minPrice = 0.0;
    private Double maxPrice = Double.MAX_VALUE;
    private Long vendedorId;
    private Long clienteId;

    public PedidoFilterDTO(Long id, ESTADO_PEDIDO estado, Double minPrice, Double maxPrice, Long clienteId, Long vendedorId) {
        this.id = id;
        this.estado = estado;
        setMinPrice(minPrice);
        setMaxPrice(maxPrice);
        this.clienteId = clienteId;
        this.vendedorId = vendedorId;
    }

    public void setMaxPrice(Double maxPrice) {
        if (maxPrice == null) {
            this.maxPrice = Double.MAX_VALUE;
            return;
        }
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(Double minPrice) {
        if (minPrice == null) {
            this.minPrice = 0.0;
            return;
        }
        this.minPrice = minPrice;
    }
}
