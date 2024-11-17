package deso.delivery_app.controllers;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.VendedorDAO;
import deso.delivery_app.persistence.filters.FiltrosVendedor;
import deso.delivery_app.utils.Coordenada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendedorControllerTest {

    private VendedorController vendedorController;
    private VendedorDAO vendedorDAO;

    @BeforeEach
    void setUp() {
        vendedorDAO = mock(VendedorDAO.class);
        vendedorController = new VendedorController();
        vendedorController.vendedorDAO = vendedorDAO; // Inyectamos el mock manualmente
    }

    @Test
    void testGetListaSinFiltros() throws ItemNoEncontradoException {
        Coordenada coord1 = new Coordenada(-40, -63);
        Coordenada coord2 = new Coordenada(-34, -58);

        Vendedor vendedor1 = new Vendedor("Pepe", "Herndarias 833", "27-40727599-8", coord1);
        Vendedor vendedor2 = new Vendedor("Laura", "Mitre 123", "20-12345678-9", coord2);

        when(vendedorDAO.filtrar(Mockito.any(FiltrosVendedor.class))).thenReturn(Arrays.asList(vendedor1, vendedor2));

        List<Vendedor> vendedores = vendedorController.getLista();

        assertEquals(2, vendedores.size());
        assertEquals("Pepe", vendedores.get(0).getNombre());
        assertEquals(coord1, vendedores.get(0).getCoordenadas());
        assertEquals("Laura", vendedores.get(1).getNombre());
        assertEquals(coord2, vendedores.get(1).getCoordenadas());
    }

    @Test
    void testGetListaConFiltros() throws ItemNoEncontradoException {
        Coordenada coord = new Coordenada(-40, -63);
        Vendedor vendedor = new Vendedor("Pepe", "Herndarias 833", "27-40727599-8", coord);

        when(vendedorDAO.filtrar(Mockito.any(FiltrosVendedor.class))).thenReturn(List.of(vendedor));

        List<Vendedor> vendedores = vendedorController.getLista("Pepe", "");

        assertEquals(1, vendedores.size());
        assertEquals("Pepe", vendedores.get(0).getNombre());
        assertEquals(coord, vendedores.get(0).getCoordenadas());
    }

    @Test
    void testCrearVendedor() {
        Coordenada coord = new Coordenada(-40, -63);
        Vendedor vendedor = new Vendedor("Pepe", "Herndarias 833", "27-40727599-8", coord);

        when(vendedorDAO.create(vendedor)).thenReturn(vendedor);

        Vendedor result = vendedorController.crear(vendedor);

        assertNotNull(result);
        assertEquals("Pepe", result.getNombre());
        assertEquals(coord, result.getCoordenadas());
        verify(vendedorDAO).create(vendedor);
    }

    @Test
    void testModificarVendedor() {
        Coordenada coordOriginal = new Coordenada(-40, -63);
        Coordenada coordModificada = new Coordenada(-34, -58);

        Vendedor vendedor = new Vendedor("Pepe", "Herndarias 833", "27-40727599-8", coordOriginal);
        Vendedor vendedorModificado = new Vendedor("Pepe", "Mitre 456", "27-40727599-8", coordModificada);

        when(vendedorDAO.update(vendedor)).thenReturn(vendedorModificado);

        Vendedor result = vendedorController.modificar(vendedor);

        assertNotNull(result);
        assertEquals("Mitre 456", result.getDireccion());
        assertEquals(coordModificada, result.getCoordenadas());
        verify(vendedorDAO).update(vendedor);
    }

    @Test
    void testEliminarVendedor() {
        long id = 1;

        doNothing().when(vendedorDAO).delete(id);

        vendedorController.eliminar(id);

        verify(vendedorDAO).delete(id);
    }

    @Test
    void testBuscarVendedor() {
        Coordenada coord = new Coordenada(-40, -63);
        Vendedor vendedor = new Vendedor("Pepe", "Herndarias 833", "27-40727599-8", coord);

        when(vendedorDAO.get(1)).thenReturn(vendedor);

        Vendedor result = vendedorController.buscar(1);

        assertNotNull(result);
        assertEquals("Pepe", result.getNombre());
        assertEquals(coord, result.getCoordenadas());
        verify(vendedorDAO).get(1);
    }

    @Test
    void testGetListaSinResultados() throws ItemNoEncontradoException {
        when(vendedorDAO.filtrar(Mockito.any(FiltrosVendedor.class))).thenThrow(ItemNoEncontradoException.class);

        List<Vendedor> vendedores = vendedorController.getLista("NoExistente", "");

        assertTrue(vendedores.isEmpty());
    }
}
