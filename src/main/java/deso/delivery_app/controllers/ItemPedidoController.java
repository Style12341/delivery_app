package deso.delivery_app.controllers;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.ItemPedido;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.ItemMenuDAO;
import deso.delivery_app.persistence.DAO.ItemPedidoDAO;
import deso.delivery_app.persistence.DAO.PedidoDAO;
import deso.delivery_app.persistence.DAO.factories.ItemMenuDAOFactory;
import deso.delivery_app.persistence.DAO.factories.ItemPedidoDAOFactory;
import deso.delivery_app.persistence.DAO.factories.PedidoDAOFactory;
import deso.delivery_app.persistence.filters.FiltrosItemPedido;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoController {
    ItemPedidoDAO itemPedidoDAO = ItemPedidoDAOFactory.getDAO();

    public List<ItemPedido> getLista(Pedido p) {
        FiltrosItemPedido filters = new FiltrosItemPedido();
        filters.addIdPedido(p.getId());
        List<ItemPedido> items = new ArrayList<ItemPedido>();
        try {
            items = itemPedidoDAO.filtrar(filters);
        } catch (ItemNoEncontradoException e) {
            //:P
        }
        return items;
    }
    public void crear(long idPedido,long idItemMenu,int cantidad) {
        ItemMenuDAO itemMenuDAO = ItemMenuDAOFactory.getDAO();
        PedidoDAO pedidoDAO = PedidoDAOFactory.getDAO();
        Pedido p = pedidoDAO.get(idPedido);
        ItemMenu im = itemMenuDAO.get(idItemMenu);
        ItemPedido ip = p.agregarItem(im,cantidad);
        itemPedidoDAO.create(ip);
        pedidoDAO.update(p);

    }
    public void crear(ItemPedido it) {
        itemPedidoDAO.create(it);
    }

    public void modificar(ItemPedido it) {
        itemPedidoDAO.update(it);
    }

    public void eliminar(long id) {
        itemPedidoDAO.delete(id);
    }

    public ItemPedido buscar(long id) {
        return itemPedidoDAO.get(id);
    }

}
