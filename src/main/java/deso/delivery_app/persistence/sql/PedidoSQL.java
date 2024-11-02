package deso.delivery_app.persistence.sql;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.exception.PedidoNoEncontradoException;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.PedidoDAO;
import deso.delivery_app.persistence.filters.FiltrosPedido;

import java.util.List;

public class PedidoSQL implements PedidoDAO {
    @Override
    public Pedido create(Pedido pedido) {
        return null;
    }

    @Override
    public Pedido get(long id) {
        return null;
    }

    @Override
    public Pedido update(Pedido pedido) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Pedido> buscarPorEstado(ESTADO_PEDIDO estado, Vendedor vendedor) throws PedidoNoEncontradoException {
        return List.of();
    }

    @Override
    public List<Pedido> filtrar(FiltrosPedido filter) throws PedidoNoEncontradoException {
        return List.of();
    }
}
