package deso.delivery_app.persistence;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.filters.FiltrosVendedor;
import deso.delivery_app.persistence.sql.VendedorSQL;
import deso.delivery_app.utils.Coordenada;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VendedorSQLTest {
    private VendedorSQL vendedorSQL;
    private long testId;
    private final ArrayList<Long> filterIds = new ArrayList<Long>();

    @BeforeAll
    void setup() {
        vendedorSQL = new VendedorSQL();
    }

    @Test
    @Order(1)
    void testCreate() {
        Vendedor vendedor = new Vendedor("Juan", "Pérez", "20304050607", new Coordenada(10.0, 20.0));

        Vendedor createdVendedor = vendedorSQL.create(vendedor);
        assertNotNull(createdVendedor);
        assertTrue(createdVendedor.getId() > 0);
        testId = createdVendedor.getId();
        System.out.println("Test ID: " + testId);

    }

    @Test
    @Order(2)
    void testGet() {
        assert testId > 0;
        System.out.println("Test ID: " + testId);
        Vendedor vendedor = vendedorSQL.get(testId);
        assertNotNull(vendedor);
        assertEquals("Juan", vendedor.getNombre());
    }

    @Test
    @Order(3)
    void testUpdate() {
        Vendedor vendedor = vendedorSQL.get(testId);
        vendedor.setDireccion("Calle Falsa 123");
        vendedorSQL.update(vendedor);
        Vendedor updatedVendedor = vendedorSQL.get(testId);
        assertNotNull(updatedVendedor);
        assertEquals("Calle Falsa 123", updatedVendedor.getDireccion());
    }

    @Test
    @Order(4)
    void testDelete() {
        vendedorSQL.delete(testId);
        Vendedor vendedor = vendedorSQL.get(testId);
        assertNull(vendedor);
    }

    @Test
    @Order(5)
    void testFiltrar(){
        Vendedor vendedor = new Vendedor("Lo de Juan", "Calle falsa 111", "11111111111", new Coordenada(10.0, 20.0));
        vendedorSQL.create(vendedor);
        filterIds.add(vendedor.getId());

        Vendedor vendedor2 = new Vendedor("Lo de María", "Calle falsa 112", "222222222222", new Coordenada(10.0, 20.0));
        vendedorSQL.create(vendedor2);
        filterIds.add(vendedor2.getId());

        Vendedor vendedor3 = new Vendedor("Lo de Ana", "Calle falsa 113", "33333333333", new Coordenada(10.0, 20.0));
        vendedorSQL.create(vendedor3);
        filterIds.add(vendedor3.getId());

        FiltrosVendedor filtros = new FiltrosVendedor();

        filtros.addDireccion("Calle falsa 111");

        List<Vendedor> lista = null;
        try {
            lista = vendedorSQL.filtrar(filtros);
        } catch (ItemNoEncontradoException e) {
            throw new RuntimeException(e);
        }
        //verificar que Juan esté en la lista
        assertTrue(lista.stream().anyMatch(v -> v.getId() == vendedor.getId()));
        //verificar que María no esté en la lista
        assertFalse(lista.stream().anyMatch(v -> v.getId() == vendedor2.getId()));
        //verificar que Ana no esté en la lista
        assertFalse(lista.stream().anyMatch(v -> v.getId() == vendedor3.getId()));

    }

    @AfterAll
    void cleanup() {
        for (long id : filterIds) {
            vendedorSQL.delete(id);
        }
    }




}
