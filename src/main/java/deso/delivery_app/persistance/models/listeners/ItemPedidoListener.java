package deso.delivery_app.persistance.models.listeners;

import deso.delivery_app.persistance.models.ItemPedido;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class ItemPedidoListener {

    @PrePersist
    @PreUpdate
    public void calculatePrecioTotal(ItemPedido itemPedido) {
        if (itemPedido.getId() != null && itemPedido.getId().getItemMenu() != null) {
            Double itemMenuPrice = itemPedido.getId().getItemMenu().getPrecio();
            Integer cantidad = itemPedido.getCantidad();
            itemPedido.setPrecioTotal(itemMenuPrice * cantidad);
        }
    }
}