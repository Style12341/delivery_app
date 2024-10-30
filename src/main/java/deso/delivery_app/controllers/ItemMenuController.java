package deso.delivery_app.controllers;

import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.persistence.DAO.ItemsMenuDAO;
import deso.delivery_app.persistence.memory.ItemsMenuMemory;

import java.awt.*;

public class ItemsMenuController {
    ItemsMenuDAO itemsMenuDAO = ItemsMenuMemory.getInstance();

    public List getLista(){
        return getLista();
    }

    public List

    public void crear(ItemMenu v) {
        itemsMenuDAO.create(v);
    }

    public void modificar(ItemMenu v) {
        itemsMenuDAO.update(v);
    }

    public void eliminar(long id) {
        itemsMenuDAO.delete(id);
    }

    public ItemMenu buscar(long id) {
        return itemsMenuDAO.get(id);
    }

}
