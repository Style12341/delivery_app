package deso.delivery_app.persistance.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import deso.delivery_app.persistance.models.composed_keys.ItemPedidoKey;
import deso.delivery_app.persistance.models.listeners.ItemPedidoListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "item_pedido")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(ItemPedidoListener.class)
@NoArgsConstructor
public class ItemPedido {
    @EmbeddedId
    private ItemPedidoKey id;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;
    public ItemPedido(ItemPedidoKey id, Integer cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }
    public Long getItemMenuId() {
        return id.getItemMenu().getId();
    }
    public Double getPrecioTotal() {
        return id.getItemMenu().getPrecio() * cantidad;
    }

}
