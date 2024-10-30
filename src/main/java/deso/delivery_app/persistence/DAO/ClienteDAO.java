package deso.delivery_app.persistence.DAO;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.persistence.filters.FiltrosCliente;
import deso.delivery_app.utils.Coordenada;

import java.util.List;

public interface ClienteDAO {
    Cliente create(Cliente cliente);

    Cliente get(long id);

    Cliente update(Cliente cliente);

    void delete(long id);

    List<Cliente> filtrar(FiltrosCliente f) throws ItemNoEncontradoException;

    List<Cliente> buscarOrdenarPorNombre(FiltrosCliente f, Boolean descendente) throws ItemNoEncontradoException;

    List<Cliente> buscarOrdenarPorProximidad(FiltrosCliente f, Coordenada coord, Boolean descendente) throws ItemNoEncontradoException;
}
