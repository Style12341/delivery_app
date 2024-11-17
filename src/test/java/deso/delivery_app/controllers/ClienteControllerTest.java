package deso.delivery_app.controllers;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.persistence.DAO.ClienteDAO;
import deso.delivery_app.persistence.filters.FiltrosCliente;
import deso.delivery_app.utils.Coordenada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    private ClienteController clienteController;
    private ClienteDAO clienteDAO;

    @BeforeEach
    void setUp() {
        clienteDAO = mock(ClienteDAO.class);
        clienteController = new ClienteController();
        clienteController.clienteDAO = clienteDAO; // Inyectamos el mock manualmente
    }

    @Test
    void testGetListaSinFiltros() throws ItemNoEncontradoException {
        Coordenada coord1 = new Coordenada(36, -86);
        Coordenada coord2 = new Coordenada(40, -74);

        Cliente cliente1 = new Cliente("Juan", "Pérez", "20304050601", "juan@gmail.com", "Calle 123", coord1);
        Cliente cliente2 = new Cliente("María", "Gómez", "30405060702", "maria@gmail.com", "Calle 456", coord2);

        when(clienteDAO.filtrar(Mockito.any(FiltrosCliente.class))).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> clientes = clienteController.getLista();

        assertEquals(2, clientes.size());
        assertEquals("Juan", clientes.get(0).getNombre());
        assertEquals("María", clientes.get(1).getNombre());
        assertEquals(coord1, clientes.get(0).getCoordenadas());
        assertEquals(coord2, clientes.get(1).getCoordenadas());
    }

    @Test
    void testGetListaConFiltros()  throws ItemNoEncontradoException {
        Coordenada coord = new Coordenada(36, -86);
        Cliente cliente = new Cliente("Juan", "Pérez", "20304050601", "juan@gmail.com", "Calle 123", coord);

        when(clienteDAO.filtrar(Mockito.any(FiltrosCliente.class))).thenReturn(List.of(cliente));

        List<Cliente> clientes = clienteController.getLista("Juan", "", "", "");

        assertEquals(1, clientes.size());
        assertEquals("Juan", clientes.get(0).getNombre());
        assertEquals(coord, clientes.get(0).getCoordenadas());
    }

    @Test
    void testCrearCliente() {
        Coordenada coord = new Coordenada(36, -86);
        Cliente cliente = new Cliente("Ana", "López", "20304050602", "ana@gmail.com", "Calle 789", coord);

        when(clienteDAO.create(cliente)).thenReturn(cliente);

        Cliente result = clienteController.crear(cliente);

        assertNotNull(result);
        assertEquals("Ana", result.getNombre());
        assertEquals(coord, result.getCoordenadas());
        verify(clienteDAO).create(cliente);
    }

    @Test
    void testModificarCliente() {
        Coordenada coordOriginal = new Coordenada(36, -86);
        Coordenada coordModificada = new Coordenada(40, -74);

        Cliente cliente = new Cliente("Ana", "López", "20304050602", "ana@gmail.com", "Calle 789", coordOriginal);
        Cliente clienteModificado = new Cliente("Ana", "García", "20304050602", "ana@gmail.com", "Calle 789", coordModificada);

        when(clienteDAO.update(cliente)).thenReturn(clienteModificado);

        Cliente result = clienteController.modificar(cliente);

        assertNotNull(result);
        assertEquals("García", result.getApellido());
        assertEquals(coordModificada, result.getCoordenadas());
        verify(clienteDAO).update(cliente);
    }

    @Test
    void testEliminarCliente() {
        long id = 1;

        doNothing().when(clienteDAO).delete(id);

        clienteController.eliminar(id);

        verify(clienteDAO).delete(id);
    }

    @Test
    void testBuscarCliente() {
        Coordenada coord = new Coordenada(36, -86);
        Cliente cliente = new Cliente("Ana", "López", "20304050602", "ana@gmail.com", "Calle 789", coord);

        when(clienteDAO.get(1)).thenReturn(cliente);

        Cliente result = clienteController.buscar(1);

        assertNotNull(result);
        assertEquals("Ana", result.getNombre());
        assertEquals(coord, result.getCoordenadas());
        verify(clienteDAO).get(1);
    }

    @Test
    void testGetListaSinResultados() throws ItemNoEncontradoException {
        when(clienteDAO.filtrar(Mockito.any(FiltrosCliente.class))).thenThrow(ItemNoEncontradoException.class);

        List<Cliente> clientes = clienteController.getLista("NoExistente", "", "", "");

        assertTrue(clientes.isEmpty());
    }
}
