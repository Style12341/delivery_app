package deso.delivery_app.persistence.DAO;

import deso.delivery_app.ItemMenu;
import deso.delivery_app.exception.ItemNoEncontradoException;

import java.util.List;

public interface ItemsMenuDAO {
    ItemMenu create(ItemMenu ItemMenu);

    ItemMenu get(long id);

    ItemMenu update(ItemMenu ItemMenu);

    void delete(long id);

    List<ItemMenu> filtrar(FiltrosItemMenu f) throws ItemNoEncontradoException;

    List<ItemMenu> buscarOrdenarPorNombre(FiltrosItemMenu f, Boolean descendente) throws ItemNoEncontradoException;

    List<ItemMenu> buscarOrdenarPorPrecio(FiltrosItemMenu f, Boolean descendente) throws ItemNoEncontradoException;
}
