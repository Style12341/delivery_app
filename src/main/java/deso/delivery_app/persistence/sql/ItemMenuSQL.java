package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.persistence.DAO.ItemMenuDAO;
import deso.delivery_app.persistence.filters.FiltrosItemMenu;

import java.util.List;

public class ItemMenuSQL implements ItemMenuDAO {
    @Override
    public ItemMenu create(ItemMenu ItemMenu) {
        return null;
    }

    @Override
    public ItemMenu get(long id) {
        return null;
    }

    @Override
    public ItemMenu update(ItemMenu ItemMenu) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<ItemMenu> filtrar(FiltrosItemMenu f) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<ItemMenu> buscarOrdenarPorNombre(FiltrosItemMenu f, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<ItemMenu> buscarOrdenarPorPrecio(FiltrosItemMenu f, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }
}
