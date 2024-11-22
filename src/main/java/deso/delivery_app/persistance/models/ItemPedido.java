package deso.delivery_app.persistance.models;

import deso.delivery_app.persistance.models.composed_keys.ItemPedidoKey;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item_pedido")
public class ItemPedido {
    @EmbeddedId
    private ItemPedidoKey id;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(name = "precio_total",nullable = false)
    private Double precioTotal;

}
