package deso.delivery_app.persistance.repository;

import deso.delivery_app.enums.ESTADO_PEDIDO;
import deso.delivery_app.persistance.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {
    void deleteAllByVendedor_IdAndEstado(Long vendedorId, ESTADO_PEDIDO estado);

    void deleteAllByCliente_IdAndEstado(Long clienteId, ESTADO_PEDIDO estado);
}
