package deso.delivery_app.persistence.DAO;

import deso.delivery_app.Coordenada;
import deso.delivery_app.Vendedor;
import deso.delivery_app.exception.ItemNoEncontradoException;

import java.util.List;

public interface VendedorDao {
    Vendedor create(Vendedor vendedor);

    Vendedor get(long id);

    Vendedor update(Vendedor vendedor);

    void delete(long id);

    List<Vendedor> filtrar(FiltrosVendedor f) throws ItemNoEncontradoException;

    List<Vendedor> buscarOrdenarPorNombre(FiltrosVendedor f, Boolean descendente) throws ItemNoEncontradoException;

    List<Vendedor> buscarOrdenarPorProximidad(FiltrosVendedor f, Coordenada coord, Boolean descendente) throws ItemNoEncontradoException;
}
