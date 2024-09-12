package deso.delivery_app.persistence.DAO;


import deso.delivery_app.ItemPedido;
import deso.delivery_app.exception.ItemNoEncontradoException;

import java.util.List;

public interface ItemsPedidoDao {
    public ItemPedido create(ItemPedido itemPedido);

    public void delete(long id);

    public List<ItemPedido> filtrar(CondicionesFiltradoItemPedido c) throws ItemNoEncontradoException;

    public List<ItemPedido> buscarOrdenarPorNombre(CondicionesFiltradoItemPedido c, Boolean descendente) throws ItemNoEncontradoException;

    public List<ItemPedido> buscarOrdenarPorPrecio(CondicionesFiltradoItemPedido c, Boolean descendente) throws ItemNoEncontradoException;
}
