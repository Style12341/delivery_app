package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.persistence.DAO.ClienteDAO;
import deso.delivery_app.persistence.filters.FiltrosCliente;
import deso.delivery_app.utils.Coordenada;

import java.util.List;

public class ClienteSQL implements ClienteDAO {
    @Override
    public Cliente create(Cliente cliente) {
        return null;
    }

    @Override
    public Cliente get(long id) {
        return null;
    }

    @Override
    public Cliente update(Cliente cliente) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Cliente> filtrar(FiltrosCliente f) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<Cliente> buscarOrdenarPorNombre(FiltrosCliente f, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<Cliente> buscarOrdenarPorProximidad(FiltrosCliente f, Coordenada coord, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }
}
