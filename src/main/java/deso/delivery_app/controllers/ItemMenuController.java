package deso.delivery_app.controllers;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.ItemMenuDAO;
import deso.delivery_app.persistence.DAO.VendedorDAO;
import deso.delivery_app.persistence.DAO.factories.ItemMenuDAOFactory;
import deso.delivery_app.persistence.DAO.factories.VendedorDAOFactory;
import deso.delivery_app.persistence.filters.FiltrosItemMenu;
import deso.delivery_app.persistence.memory.ItemMenuMemory;
import deso.delivery_app.persistence.memory.VendedorMemory;

import java.util.ArrayList;
import java.util.List;

public class ItemMenuController {
    ItemMenuDAO itemMenuDAO = ItemMenuDAOFactory.getDAO();
    VendedorDAO vendedorDAO = VendedorDAOFactory.getDAO();
    public static final int NO_FILTRAR_VENDEDOR = -1;

    public List<ItemMenu> getLista() {
        return getLista("", TIPO_ITEM.TODOS, 0, Double.MAX_VALUE, NO_FILTRAR_VENDEDOR, "", false, false);
    }

    public List<ItemMenu> getLista(String nombreItem, TIPO_ITEM categoria, double precioMinimo, double precioMaximo, long idVendedor, String nombreVendedor, boolean filtrarAptoCeliaco, boolean filtrarAptoVegano) {
        FiltrosItemMenu filters = new FiltrosItemMenu();
        filters.addNombre(nombreItem);
        switch (categoria) {
            case TIPO_ITEM.COMIDA -> filters.addComida();
            case TIPO_ITEM.BEBIDA -> filters.addBebida();
        }
        filters.addRangoPrecio(precioMinimo, precioMaximo);
        filters.addNombreVendedor(nombreVendedor);
        if (filtrarAptoVegano) filters.addComidaVegana();
        if (filtrarAptoCeliaco) filters.addComidaCeliaca();
        if (idVendedor != NO_FILTRAR_VENDEDOR) {
            filters.addIdVendedor(idVendedor);
        }
        List<ItemMenu> ims = new ArrayList<ItemMenu>();
        try {
            ims = itemMenuDAO.filtrar(filters);
        } catch (ItemNoEncontradoException e) {
            // :P
        }
        return ims;
    }

    public List<ItemMenu> getLista(long idVendedor) {
        return getLista("", TIPO_ITEM.TODOS, 0, Double.MAX_VALUE, idVendedor, "", false, false);
    }

    public void crear(ItemMenu i, long idVendedor) {
        Vendedor v = vendedorDAO.get(idVendedor);
        i.setVendedor(v);
        v.addItemToMenu(i);
        itemMenuDAO.create(i);
    }

    public void modificar(ItemMenu i) {
        itemMenuDAO.update(i);
    }

    public void eliminar(long id) {
        itemMenuDAO.delete(id);
    }

    public ItemMenu buscar(long id) {
        return itemMenuDAO.get(id);
    }

}
