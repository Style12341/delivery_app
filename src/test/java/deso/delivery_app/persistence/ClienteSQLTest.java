package deso.delivery_app.persistence;


import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.persistence.filters.FiltrosCliente;
import deso.delivery_app.persistence.sql.ClienteSQL;
import deso.delivery_app.utils.Coordenada;
import org.junit.jupiter.api.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteSQLTest {
    private ClienteSQL clienteSQL;
    private long testId;
    private ArrayList<Long>arrayIds = new ArrayList<Long>();

    @BeforeAll
    void setup() {
        clienteSQL = new ClienteSQL();
    }

    @Test
    @Order(1)
    void testCreate() {
        Cliente cliente = new Cliente(
                "Juan",
                "Pérez",
                "20304050607",
                "juan.perez@example.com",
                "Calle Falsa 123",
                new Coordenada(10.0, 20.0)
        );

        Cliente createdCliente = clienteSQL.create(cliente);
        assertNotNull(createdCliente);
        assertTrue(createdCliente.getId() > 0);
        testId = createdCliente.getId();
        System.out.println("Test ID: " + testId);
    }

    @Test
    @Order(2)
    void testGet() {
        assert testId > 0;
        System.out.println("Test ID: " + testId);
        Cliente cliente = clienteSQL.get(testId);
        assertNotNull(cliente);
        assertEquals("Juan", cliente.getNombre());
    }

    @Test
    @Order(3)
    void testUpdate() {
        Cliente cliente = clienteSQL.get(testId);
        cliente.setApellido("Gómez");

        Cliente updatedCliente = clienteSQL.update(cliente);
        assertNotNull(updatedCliente);
        assertEquals("Gómez", updatedCliente.getApellido());
    }

    @Test
    @Order(4)
    void testDelete() {
        clienteSQL.delete(testId);
        Cliente cliente = clienteSQL.get(testId);
        assertNull(cliente);
    }

    @Test
    @Order(5)
    void testFiltrar() {
        Cliente cliente = new Cliente(
                "María",
                "López",
                "22222222222",
                "maria.lopez@example.com",
                "Calle Verdadera 456",
                new Coordenada(15.0, 25.0)
        );
        Cliente cliente2 = new Cliente(
                "Juan",
                "Damian",
                "11111111111",
                "Juan.Damian@example.com",
                "Calle Verdadera 456",
                new Coordenada(15.0, 25.0)
        );
        Cliente cliente3 = new Cliente(
                "Esteban",
                "Seba",
                "00000000000",
                "esteban.seba@example.com",
                "Calle Verdadera 456",
                new Coordenada(15.0, 25.0)
        );
        Cliente createdCliente = clienteSQL.create(cliente);
        arrayIds.add(createdCliente.getId());

        Cliente createdCliente2 = clienteSQL.create(cliente2);
        arrayIds.add(createdCliente2.getId());

        Cliente createdCliente3 = clienteSQL.create(cliente3);
        arrayIds.add(createdCliente3.getId());

        FiltrosCliente filtros = new FiltrosCliente();
        filtros.addNombre("María");
        List<Cliente> clientes = null;
        try {
            clientes = clienteSQL.filtrar(filtros);
        } catch (ItemNoEncontradoException e) {
            throw new RuntimeException(e);
        }
        // Verificar que maria esté en la lista
        assertTrue(clientes.stream().anyMatch(c -> c.getNombre().equals("María")));
        // Verificar que juan no esté en la lista
        assertFalse(clientes.stream().anyMatch(c -> c.getNombre().equals("Juan")));
        // Verificar que esteban no esté en la lista
        assertFalse(clientes.stream().anyMatch(c -> c.getNombre().equals("Esteban")));
    }
     @Test
    @Order(6)
     void testNotRepeatCuit() {
         List<Cliente> clientes = null;
         try {
            clientes = clienteSQL.filtrar(new FiltrosCliente());
         } catch (ItemNoEncontradoException e) {
             throw new RuntimeException(e);
         }

         //Iterar sobre los clientes y verificar que no haya dos con el mismo CUIT
            for (int i = 0; i < clientes.size(); i++) {
                for (int j = i + 1; j < clientes.size(); j++) {
                    assertNotEquals(clientes.get(i).getCuit(), clientes.get(j).getCuit());
                }
            }
            //Crear un cliente con un CUIT ya existente
            Cliente cliente = new Cliente(
                "Juan",
                "Pérez",
                clientes.getFirst().getCuit(),
                    "juan2@gmail.com",
                "Calle Falsa 123",
                new Coordenada(10.0, 20.0)
            );

        }
         

    @AfterAll
    void cleanup() {
        // Limpieza de datos creados en las pruebas
        Cliente cliente = clienteSQL.get(testId);
        if (cliente != null) {
            clienteSQL.delete(testId);
        }
        for (Long id : arrayIds) {
            cliente = clienteSQL.get(id);
            if (cliente != null) {
                clienteSQL.delete(id);
            }
        }

    }
}
