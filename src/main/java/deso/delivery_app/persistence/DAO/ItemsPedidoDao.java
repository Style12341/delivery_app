package deso.delivery_app.persistence.DAO;


import deso.delivery_app.ItemPedido;
import deso.delivery_app.exception.ItemNoEncontradoException;

import java.util.List;

public interface ItemsPedidoDao {
    ItemPedido create(ItemPedido itemPedido);

    void delete(long id);

    List<ItemPedido> filtrar(FiltrosItemPedido f) throws ItemNoEncontradoException;

    List<ItemPedido> buscarOrdenarPorNombre(FiltrosItemPedido f, Boolean descendente) throws ItemNoEncontradoException;

    List<ItemPedido> buscarOrdenarPorPrecio(FiltrosItemPedido f, Boolean descendente) throws ItemNoEncontradoException;
}
