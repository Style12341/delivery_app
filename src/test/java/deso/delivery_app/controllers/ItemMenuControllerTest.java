package deso.delivery_app.controllers;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Bebida;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.Plato;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.ItemMenuDAO;
import deso.delivery_app.persistence.DAO.VendedorDAO;
import deso.delivery_app.utils.Coordenada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ItemMenuControllerTest {

    private ItemMenuController controller;
    private ItemMenuDAO mockItemMenuDAO;
    private VendedorDAO mockVendedorDAO;

    @BeforeEach
    void setUp(){
        mockItemMenuDAO = Mockito.mock(ItemMenuDAO.class);
        mockVendedorDAO = Mockito.mock(VendedorDAO.class);

        controller = new ItemMenuController();
        controller.itemMenuDAO = mockItemMenuDAO;
        controller.vendedorDAO = mockVendedorDAO;
    }

    @Test
    public void testGetLista() throws ItemNoEncontradoException {
        ItemMenu item1 = new Bebida("Coca-Cola", "Bebida gaseosa clásica", 2.50, 500.0, 0, true, true);
        ItemMenu item2 = new Bebida("Cerveza Corona", "Cerveza rubia ligera", 3.99, 355.0, 4.5, false, false);
        ItemMenu item3 = new Bebida("Agua Mineral", "Agua embotellada sin gas", 1.50, 600.0, 0, false, true);
        ItemMenu item4 = new Plato("Risotto de hongos", "Risotto cremoso con champiñones y queso parmesano", 10.75, 350.0, false, true);
        ItemMenu item5 = new Plato("Wrap de falafel", "Wrap de garbanzos, hummus, y vegetales frescos", 7.99, 300.0, true, false);
        when(mockItemMenuDAO.filtrar(any())).thenReturn(Arrays.asList(item1,item2,item3,item4,item5));

        List<ItemMenu> result = controller.getLista();

        assertEquals(5, result.size());
        assertEquals("Coca-Cola", result.get(0).getNombre());
        assertEquals("Cerveza Corona", result.get(1).getNombre());
        assertEquals("Agua Mineral", result.get(2).getNombre());
        assertEquals("Risotto de hongos", result.get(3).getNombre());

        verify(mockItemMenuDAO, times(1)).filtrar(any());
    }

    @Test
    void testCrearItemMenu(){
        Vendedor vendedor = new Vendedor("La Dominga", "Herndarias 833", "27-40727599-8", new Coordenada(-40, -63));
        ItemMenu item1 = new Bebida("Coca-Cola", "Bebida gaseosa clásica", 2.50, 500.0, 0, true, true);

        when(mockVendedorDAO.get(1)).thenReturn(vendedor);
        when(mockItemMenuDAO.create(any())).thenReturn(item1);

        ItemMenu result = controller.crear(item1,1);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getNombre());
        verify(mockVendedorDAO, times(1)).get(1);
        verify(mockItemMenuDAO, times(1)).create(item1);
    }

    @Test
    void testBuscarItemMenu(){
        ItemMenu item1 = new Bebida("Coca-Cola", "Bebida gaseosa clásica", 2.50, 500.0, 0, true, true);

        when(mockItemMenuDAO.get(1)).thenReturn(item1);
        ItemMenu result = controller.buscar(1);
        assertEquals("Coca-Cola", result.getNombre());
        verify(mockItemMenuDAO, times(1)).get(1);
    }


    @Test
    void testModificarItemMenu(){
        ItemMenu item1 = new Bebida("Coca-Cola", "Bebida gaseosa clásica", 2.50, 500.0, 0, true, true);

        //TODO
    }

}
