package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.VendedorDAO;
import deso.delivery_app.persistence.filters.FiltrosVendedor;
import deso.delivery_app.utils.Coordenada;

import java.util.List;

public class VendedorSQL implements VendedorDAO {
    @Override
    public Vendedor create(Vendedor vendedor) {
        return null;
    }

    @Override
    public Vendedor get(long id) {
        return null;
    }

    @Override
    public Vendedor update(Vendedor vendedor) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Vendedor> filtrar(FiltrosVendedor f) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<Vendedor> buscarOrdenarPorNombre(FiltrosVendedor f, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<Vendedor> buscarOrdenarPorProximidad(FiltrosVendedor f, Coordenada coord, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }
}
