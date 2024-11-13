package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.ItemPedido;
import deso.delivery_app.persistence.DAO.ItemPedidoDAO;
import deso.delivery_app.persistence.filters.FiltrosItemPedido;

import java.util.List;

public class ItemPedidoSQL implements ItemPedidoDAO {
    @Override
    public ItemPedido create(ItemPedido itemPedido) {
        return null;
    }

    @Override
    public ItemPedido get(long idPedido, long idItemMenu) {
        return null;
    }

    @Override
    public ItemPedido update(ItemPedido itemPedido) {
        return null;
    }

    @Override
    public void delete(long idPedido, long idItemMenu) {

    }

    @Override
    public List<ItemPedido> filtrar(FiltrosItemPedido f) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<ItemPedido> buscarOrdenarPorNombre(FiltrosItemPedido f, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<ItemPedido> buscarOrdenarPorPrecio(FiltrosItemPedido f, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }
}
