package deso.delivery_app.controllers;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.ItemsMenuDAO;
import deso.delivery_app.persistence.DAO.VendedorDAO;
import deso.delivery_app.persistence.filters.FiltrosItemMenu;
import deso.delivery_app.persistence.memory.ItemsMenuMemory;
import deso.delivery_app.persistence.memory.VendedorMemory;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class ItemMenuController {
    ItemsMenuDAO itemsMenuDAO = ItemsMenuMemory.getInstance();
    VendedorDAO vendedorDAO = VendedorMemory.getInstance();
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
            ims = itemsMenuDAO.filtrar(filters);
        } catch (ItemNoEncontradoException e) {
            // :P
        }
        return ims;
    }

    public void crear(ItemMenu i, long idVendedor) {
        Vendedor v = vendedorDAO.get(idVendedor);
        i.setVendedor(v);
        v.addItemToMenu(i);
        itemsMenuDAO.create(i);
    }

    public void modificar(ItemMenu i) {
        itemsMenuDAO.update(i);
    }

    public void eliminar(long id) {
        itemsMenuDAO.delete(id);
    }

    public ItemMenu buscar(long id) {
        return itemsMenuDAO.get(id);
    }

}
