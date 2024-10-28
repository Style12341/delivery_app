package deso.delivery_app.persistence.DAO;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.exception.PedidoNoEncontradoException;

import java.util.List;

public interface PedidosDao {
    Pedido create(Pedido pedido);

    Pedido get(long id);

    Pedido update(Pedido pedido);

    void delete(long id);

    List<Pedido> buscarPorEstado(ESTADO_PEDIDO estado, Vendedor vendedor) throws PedidoNoEncontradoException;
}
