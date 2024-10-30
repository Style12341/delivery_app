package deso.delivery_app.persistence.memory;

import deso.delivery_app.persistence.DAO.ClienteDAO;
import deso.delivery_app.persistence.filters.FiltrosCliente;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.exception.ItemNoEncontradoException;

import java.util.ArrayList;
import java.util.List;

public class ClienteMemory implements ClienteDAO {
    ArrayList<Cliente> clienteMemory = new ArrayList<>();
    private static ClienteMemory SINGLETON_INSTANCE;

    private ClienteMemory() {
        clienteMemory = new ArrayList<>();
    }

    public static ClienteMemory getInstance() {
        if (SINGLETON_INSTANCE == null) SINGLETON_INSTANCE = new ClienteMemory();
        return SINGLETON_INSTANCE;
    }

    @Override
    public Cliente create(Cliente vendedor) {
        clienteMemory.add(vendedor);
        return vendedor;
    }

    @Override
    public Cliente get(long id) {
        return clienteMemory.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Cliente update(Cliente vendedor) {
        for (int i = 0; i < clienteMemory.size(); i++) {
            if (clienteMemory.get(i).getId() == vendedor.getId()) {
                clienteMemory.set(i, vendedor);
                return vendedor;
            }
        }
        return null;
    }

    @Override
    public void delete(long id) {
        clienteMemory.removeIf(v -> v.getId() == id);
    }

    @Override
    public List<Cliente> filtrar(FiltrosCliente f) throws ItemNoEncontradoException {
        // Si es comida o bebida ->
        List<Cliente> lista = clienteMemory.stream().filter(f.getFiltros()).toList();
        if (lista.isEmpty()) throw new ItemNoEncontradoException("Item no encontrado");
        return lista;
    }

    @Override
    public List<Cliente> buscarOrdenarPorNombre(FiltrosCliente f, Boolean descendente) throws ItemNoEncontradoException {
        List<Cliente> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        // CompareTo devuelve negativo si es menor y positivo si es mayor, para invertirlo se multiplica por -1
        return lista.stream().sorted((v1, v2) -> v2.getNombre().compareTo(v1.getNombre()) * desc).toList();
    }

    @Override
    public List<Cliente> buscarOrdenarPorProximidad(FiltrosCliente f, Coordenada coord, Boolean descendente) throws ItemNoEncontradoException {
        List<Cliente> lista = this.filtrar(f);
        // Se ordena por distancia entre coord y coordenada de Cliente
        int desc = descendente ? 1 : -1;
        return lista.stream().sorted((v1, v2) -> (int) (Double.compare(coord.calcularDistancia(v1.getCoordenadas()), coord.calcularDistancia(v2.getCoordenadas()))) * desc).toList();
    }
}
