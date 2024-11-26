package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.ItemPedido;
import deso.delivery_app.persistance.models.composed_keys.ItemPedidoKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoKey> {
}
