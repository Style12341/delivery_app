package deso.delivery_app.persistence.DAO;


import deso.delivery_app.models.ItemPedido;
import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.persistence.filters.FiltrosItemPedido;

import java.util.List;

public interface ItemPedidoDAO {
    ItemPedido create(ItemPedido itemPedido);

    ItemPedido get(long idPedido, long idItemMenu);

    ItemPedido update(ItemPedido itemPedido);

    void delete(long idPedido, long idItemMenu);

    List<ItemPedido> filtrar(FiltrosItemPedido f) throws ItemNoEncontradoException;

    List<ItemPedido> buscarOrdenarPorNombre(FiltrosItemPedido f, Boolean descendente) throws ItemNoEncontradoException;

    List<ItemPedido> buscarOrdenarPorPrecio(FiltrosItemPedido f, Boolean descendente) throws ItemNoEncontradoException;
}
