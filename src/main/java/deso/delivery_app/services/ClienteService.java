package deso.delivery_app.services;

import deso.delivery_app.persistance.models.Cliente;

import java.util.List;

public interface ClienteService {
    public List<Cliente> getAllClientes();

    public List<Cliente> getMatchingClientes(Cliente clienteFilter);

    public Cliente getClienteById(Long id);

    public Cliente createCliente(Cliente cliente);

    public Cliente updateCliente(Long id, Cliente cliente);

    public void deleteCliente(Long id);


}
