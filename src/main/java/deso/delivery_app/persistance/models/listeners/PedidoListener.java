package deso.delivery_app.persistance.models.listeners;

import deso.delivery_app.persistance.models.ItemPedido;
import deso.delivery_app.persistance.models.Pedido;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class PedidoListener {

    @PrePersist
    @PreUpdate
    public void calculatePrecioAcumulado(Pedido pedido) {
        double total = 0.0;
        for (ItemPedido item : pedido.getItems()) {
            total += item.getPrecioTotal();
        }
        pedido.setPrecioAcumulado(total);
    }
}