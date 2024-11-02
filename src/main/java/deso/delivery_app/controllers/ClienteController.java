package deso.delivery_app.controllers;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.persistence.DAO.ClienteDAO;
import deso.delivery_app.persistence.DAO.factories.ClienteDAOFactory;
import deso.delivery_app.persistence.filters.FiltrosCliente;
import deso.delivery_app.persistence.memory.ClienteMemory;

import java.util.ArrayList;
import java.util.List;

public class ClienteController {
    ClienteDAO clienteDAO = ClienteDAOFactory.getDAO();
    public List<Cliente> getLista(){
        return getLista("", "", "", "");
    }
    public List<Cliente> getLista(String nombre, String apellido, String email, String direccion) {
        FiltrosCliente filters = new FiltrosCliente();
        filters.addNombre(nombre);
        filters.addApellido(apellido);
        filters.addDireccion(direccion);
        filters.addEmail(email);
        List<Cliente> cs = new ArrayList<Cliente>();
        try {
            cs = clienteDAO.filtrar(filters);
        } catch (ItemNoEncontradoException e) {
            //:P
        }
        return cs;
    }

    public void crear(Cliente c) {
        clienteDAO.create(c);
    }

    public void modificar(Cliente c) {
        clienteDAO.update(c);
    }


    public void eliminar(long id) {
        clienteDAO.delete(id);
    }


    public Cliente buscar(long id) {
        return clienteDAO.get(id);
    }
}
