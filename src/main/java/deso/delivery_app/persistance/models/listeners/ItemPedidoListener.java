package deso.delivery_app.persistance.models.listeners;

import deso.delivery_app.persistance.models.ItemPedido;
import deso.delivery_app.persistance.models.Pedido;
import deso.delivery_app.persistance.repository.ItemPedidoRepository;
import deso.delivery_app.persistance.repository.PedidoRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ItemPedidoListener {

    @PrePersist
    @PreUpdate
    public void setTotalPrice(ItemPedido itemPedido) {
        itemPedido.setPrecioTotal(itemPedido.getId().getItemMenu().getPrecio() * itemPedido.getCantidad());
    }


}