package deso.delivery_app.testing;

import deso.delivery_app.*;
import deso.delivery_app.persistence.DAO.FiltrosItemPedido;
import deso.delivery_app.persistence.DAO.ItemsPedidoMemory;
import deso.delivery_app.utils.*;
import deso.delivery_app.exception.ItemNoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Entrega3 {
    public static ArrayList<Vendedor> vendedores = new ArrayList<>();
    public static ArrayList<ItemMenu> itemsBebidas = new ArrayList<>();
    public static ArrayList<ItemMenu> itemsComidas = new ArrayList<>();
    public static ArrayList<Cliente> clientes = new ArrayList<>();
    public static ArrayList<Pedido> pedidos = new ArrayList<>();

    public static ItemsPedidoMemory itemsPedidoMemory = ItemsPedidoMemory.getItemsPedidoMemory();


    public static void run() {
        // Magic
        // 10 ItemMenu
        createItems();
        // 2 Clientes
        createClientes();
        // 2 Vendedores
        createVendedores();
        // 5 Pedidos
        createPedidos();

        // Caso 1 filtrar por apellido de cliente 1
        String apellidoCliente1 = clientes.getFirst().getApellido();
        System.out.println("Caso 1: filtrar por apellido de cliente 1, Cliente: " + apellidoCliente1);
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addApellidoCliente(apellidoCliente1);
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Ensalada Mixta, Cerveza Corona, Wrap de falafel, Fanta Naranja, Milanesa con papas fritas, Coca-Cola
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos para el cliente 1");
        }

        // Caso 2 filtrar por apellido de cliente 2
        String apellidoCliente2 = clientes.get(1).getApellido();
        System.out.println("\nCaso 2: filtrar por apellido de cliente 2, Cliente: " + apellidoCliente2);
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addApellidoCliente(apellidoCliente2);
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Tarta de espinaca, Agua Mineral, Gin Tonic
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos para el cliente 2");
        }
        // Caso 3 filtrar por nombre de vendedor 1
        String nombreVendedor1 = vendedores.getFirst().getNombre();
        System.out.println("\nCaso 3: filtrar por nombre de vendedor 1, Vendedor: " + nombreVendedor1);
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addNombreVendedor(nombreVendedor1);
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Ensalada Mixta, Cerveza Corona, Wrap de falafel, Fanta Naranja, Milanesa con papas fritas, Coca-Cola
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos para el vendedor 1");
        }
        // Caso 4 filtrar por nombre de vendedor 2
        String nombreVendedor2 = vendedores.get(1).getNombre();
        System.out.println("\nCaso 4: filtrar por nombre de vendedor 2, Vendedor: " + nombreVendedor2);
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addNombreVendedor(nombreVendedor2);
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Tarta de espinaca, Agua Mineral, Gin Tonic
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos para el vendedor 2");
        }
        // Caso 5 filtrar por nombre de vendedor 1 y cuit vendedor 2 da vacio
        System.out.println("\nCaso 5: filtrar por nombre de vendedor 1 y cuit vendedor 2 deberia dar vacio");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addNombreVendedor(nombreVendedor1);
            filtros.addCuitVendedor(vendedores.get(1).getCuit());
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar vacio
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos para el vendedor 1 y cuit vendedor 2");
        }
        // Caso 6 deberia devolver todos los items pedidos existentes ordenados por nombre
        System.out.println("\nCaso 6: deberia devolver todos los items pedidos existentes ordenados por nombre");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.buscarOrdenarPorNombre(filtros, false);
            // Esto deberia dar Ensalada Mixta, Fanta Naranja, Gin Tonic, Milanesa con papas fritas, Pizza Margarita, Tarta de espinaca, Wrap de falafel
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 7 deberia devolver todos los items pedidos existentes ordenados por precio
        System.out.println("\nCaso 7: deberia devolver todos los items pedidos existentes ordenados por precio");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.buscarOrdenarPorPrecio(filtros, false);
            // Esto deberia dar Agua Mineral, Coca-Cola, Fanta Naranja, Ensalada Mixta, Tarta de espinaca, Milanesa con papas fritas, Pizza Margarita
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + "(" + itemPedido.getItemMenu().getPrecio() + ")" + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 8 deberia devolver todos los items pedidos existentes ordenados por precio descendente
        System.out.println("\nCaso 8: deberia devolver todos los items pedidos existentes ordenados por precio descendente");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.buscarOrdenarPorPrecio(filtros, true);
            // Esto deberia dar Pizza Margarita, Milanesa con papas fritas, Tarta de espinaca, Ensalada Mixta, Fanta Naranja, Coca-Cola, Agua Mineral
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + "(" + itemPedido.getItemMenu().getPrecio() + ")" + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 9 deberia devolver todos los items pedidos existentes ordenados por precio descendente y filtrados por comida vegana
        System.out.println("\nCaso 9: deberia devolver todos los items pedidos existentes ordenados por precio descendente y filtrados por comida vegana");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addComidaVegana();
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.buscarOrdenarPorPrecio(filtros, true);
            // Esto deberia dar Wrap de falafel, Ensalada Mixta, Tarta de espinaca
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + "(" + itemPedido.getItemMenu().getPrecio() + ")" + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 10 deberia devolver todos los items pedidos existentes ordenados por precio descendente y filtrados por comida celiaca
        System.out.println("\nCaso 10: deberia devolver todos los items pedidos existentes ordenados por precio descendente y filtrados por comida celiaca");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addComidaCeliaca();
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.buscarOrdenarPorPrecio(filtros, true);
            // Esto deberia dar Wrap de falafel, Ensalada Mixta, Tarta de espinaca
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + "(" + itemPedido.getItemMenu().getPrecio() + ")" + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 11 bebidas no alcoholicas
        System.out.println("\nCaso 11: deberia devolver todas las bebidas no alcoholicas");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addNoAlcoholica();
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Agua Mineral, Coca-Cola, Fanta Naranja, Jugo de naranja
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 12 gaseosas
        System.out.println("\nCaso 12: deberia devolver todas las gaseosas");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addGaseosas();
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Coca-Cola, Fanta Naranja
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 13 Comidas
        System.out.println("\nCaso 13: deberia devolver todas las comidas");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addComida();
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Ensalada Mixta, Tarta de espinaca, Wrap de falafel, Risotto de hongos, Milanesa con papas fritas, Pizza Margarita
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 14 Filtrando por rango de precios
        System.out.println("\nCaso 14: deberia devolver todas las comidas con precio entre 5 y 10");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addRangoPrecio(5, 10);
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Ensalada Mixta, Tarta de espinaca, Wrap de falafel, Milanesa con papas fritas, Pizza Margarita
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + "(" + itemPedido.getItemMenu().getPrecio() + ")" + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 15 Filtrado mayor a 5 Precio
        System.out.println("\nCaso 15: deberia devolver todas las comidas con precio mayor a 5");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addPrecioMinimo(5);
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Ensalada Mixta, Tarta de espinaca, Wrap de falafel, Risotto de hongos, Milanesa con papas fritas, Pizza Margarita
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + "(" + itemPedido.getItemMenu().getPrecio() + ")" + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
        // Caso 16 Filtrado menor a 5 Precio
        System.out.println("\nCaso 16: deberia devolver todas las comidas con precio menor a 5");
        try {
            FiltrosItemPedido filtros = new FiltrosItemPedido();
            filtros.addPrecioMaximo(5);
            List<ItemPedido> itemsPedidoFiltrados = itemsPedidoMemory.filtrar(filtros);
            // Esto deberia dar Ensalada Mixta
            for (ItemPedido itemPedido : itemsPedidoFiltrados) {
                System.out.print(itemPedido.getItemMenu().getNombre() + "(" + itemPedido.getItemMenu().getPrecio() + ")" + ", ");
            }
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron items de pedidos");
        }
    }

    private static void createVendedores() {
        // Vendedor 1
        vendedores.add(new Vendedor("La Dominga", "Herndarias 833", "27-40727599-8", new Coordenada(-40, -63)));
        for (int i = 0; i < itemsBebidas.size() / 2; i++) {
            vendedores.getFirst().addItemToMenu(itemsBebidas.get(i));
        }
        for (int i = 0; i < itemsComidas.size() / 2; i++) {
            vendedores.getFirst().addItemToMenu(itemsComidas.get(i));
        }
        // Vendedor 2
        vendedores.add(new Vendedor("Lo de Mario", "General Paz 6000", "26-13787921-6", new Coordenada(-40.5, -63.5)));
        for (int i = itemsBebidas.size() / 2; i < itemsBebidas.size(); i++) {
            vendedores.get(1).addItemToMenu(itemsBebidas.get(i));
        }
        for (int i = itemsComidas.size() / 2; i < itemsComidas.size(); i++) {
            vendedores.get(1).addItemToMenu(itemsComidas.get(i));
        }


    }

    private static void createItems() {
        // Comidas
        itemsComidas.add(new Plato("Ensalada Mixta", "Lechuga, tomate, cebolla, zanahoria", 5.99, 300.0, true, true));
        itemsComidas.add(new Plato("Milanesa con papas fritas", "Milanesa de carne con papas fritas", 9.50, 450.0, false, false));
        itemsComidas.add(new Plato("Pizza Margarita", "Pizza con queso, tomate y albahaca", 8.25, 400.0, false, false));
        itemsComidas.add(new Plato("Tarta de espinaca", "Tarta con relleno de espinaca y ricota", 6.80, 350.0, true, false));
        itemsComidas.add(new Plato("Risotto de hongos", "Risotto cremoso con champiñones y queso parmesano", 10.75, 350.0, false, true));
        itemsComidas.add(new Plato("Wrap de falafel", "Wrap de garbanzos, hummus, y vegetales frescos", 7.99, 300.0, true, false));
        // Bebidas
        itemsBebidas.add(new Bebida("Coca-Cola", "Bebida gaseosa clásica", 2.50, 500.0, 0, true, true));
        itemsBebidas.add(new Bebida("Cerveza Corona", "Cerveza rubia ligera", 3.99, 355.0, 4.5, false, false));
        itemsBebidas.add(new Bebida("Agua Mineral", "Agua embotellada sin gas", 1.50, 600.0, 0, false, true));
        itemsBebidas.add(new Bebida("Vino Tinto", "Vino tinto de la casa", 8.50, 750.0, 13.5, false, true));
        itemsBebidas.add(new Bebida("Fanta Naranja", "Bebida gaseosa con sabor a naranja", 2.80, 500.0, 0, true, true));
        itemsBebidas.add(new Bebida("Gin Tonic", "Gin mezclado con agua tónica y una rodaja de limón", 6.50, 400.0, 12.0, true, true));
        itemsBebidas.add(new Bebida("Jugo de naranja", "Jugo natural exprimido de naranjas frescas", 3.00, 350.0, 0, false, true));

    }

    private static void createClientes() {
        // Cliente 1
        clientes.add(new Cliente("Pepito", "Perez", "27-28033214-8", "pepe@test.com", "Herndarias 836", new Coordenada(-40, -63.5)));
        // Cliente 2
        clientes.add(new Cliente("Marito", "Ledesma", "27-28033414-8", "mario@test.com", "General Paz 6002", new Coordenada(-40.5, -64)));
    }

    private static void createPedidos() {

        // Pedido 1
        ArrayList<Pair<ItemMenu, Integer>> itemsPedido1 = new ArrayList<Pair<ItemMenu, Integer>>();
        itemsPedido1.add(new Pair<>(itemsComidas.get(0), 2)); // Ensalada Mixta, 2 unidades
        itemsPedido1.add(new Pair<>(itemsBebidas.get(1), 1)); // Cerveza Corona, 1 unidad

        pedidos.add(new Pedido(vendedores.get(0), clientes.get(0), itemsPedido1)); // Pedido del cliente 1 con el vendedor 1

        // Pedido 2
        ArrayList<Pair<ItemMenu, Integer>> itemsPedido2 = new ArrayList<>();
        itemsPedido2.add(new Pair<>(itemsComidas.get(3), 1)); // Tarta de espinaca, 1 unidad
        itemsPedido2.add(new Pair<>(itemsBebidas.get(6), 3)); // Jugo de naranja, 3 unidades

        pedidos.add(new Pedido(vendedores.get(1), clientes.get(1), itemsPedido2)); // Pedido del cliente 2 con el vendedor 2

        // Pedido 3
        ArrayList<Pair<ItemMenu, Integer>> itemsPedido3 = new ArrayList<>();
        itemsPedido3.add(new Pair<>(itemsComidas.get(5), 1)); // Wrap de falafel, 1 unidad
        itemsPedido3.add(new Pair<>(itemsBebidas.get(4), 2)); // Fanta Naranja, 2 unidades

        pedidos.add(new Pedido(vendedores.get(1), clientes.get(0), itemsPedido3)); // Pedido del cliente 1 con el vendedor 2

        // Pedido 4
        ArrayList<Pair<ItemMenu, Integer>> itemsPedido4 = new ArrayList<>();
        itemsPedido4.add(new Pair<>(itemsComidas.get(4), 2)); // Risotto de hongos, 2 unidades
        itemsPedido4.add(new Pair<>(itemsBebidas.get(5), 1)); // Gin Tonic, 1 unidad

        pedidos.add(new Pedido(vendedores.get(0), clientes.get(1), itemsPedido4)); // Pedido del cliente 2 con el vendedor 1

        // Pedido 5
        ArrayList<Pair<ItemMenu, Integer>> itemsPedido5 = new ArrayList<>();
        itemsPedido5.add(new Pair<>(itemsComidas.get(1), 1)); // Milanesa con papas fritas, 1 unidad
        itemsPedido5.add(new Pair<>(itemsBebidas.get(0), 2)); // Coca-Cola, 2 unidades

        pedidos.add(new Pedido(vendedores.get(0), clientes.get(0), itemsPedido5)); // Pedido del cliente 1 con el vendedor 1
    }


}
