package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.ItemPedido;
import deso.delivery_app.persistance.models.composed_keys.ItemPedidoKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoKey> {
    @Modifying
    @Transactional
    @Query("delete from ItemPedido i where i.id.pedido.id = ?1")
    public void deleteAllByPedidoId(Long id);
}
