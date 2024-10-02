package deso.delivery_app.testing;

import deso.delivery_app.*;
import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.exception.PedidoNoEncontradoException;
import deso.delivery_app.persistence.DAO.*;
import deso.delivery_app.strategies.PagarConMercadoPago;
import deso.delivery_app.strategies.PagarConTransferencia;
import deso.delivery_app.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Entrega5 {

    public static ArrayList<Vendedor> vendedores = new ArrayList<>();
    public static ArrayList<ItemMenu> itemsBebidas = new ArrayList<>();
    public static ArrayList<ItemMenu> itemsComidas = new ArrayList<>();
    public static ArrayList<Cliente> clientes = new ArrayList<>();
    public static ArrayList<Pedido> pedidos = new ArrayList<>();


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

        // Cliente elige un vendedor
        Cliente cliente = clientes.getFirst();
        Vendedor vendedorElegido = vendedores.getFirst();
        System.out.println(cliente.toString() + " elige a " + vendedorElegido.toString());

        // Cliente elige itemsMenus del vendedor elegido
        ArrayList<Pair<ItemMenu, Integer>> itemsPedido = new ArrayList<>();
        Pedido pedido = new Pedido(vendedorElegido, cliente, itemsPedido);
        ItemsMenuDAO itemsMenuDAO = ItemsMenuMemory.getInstance();
        FiltrosItemMenu filtros = new FiltrosItemMenu();
        filtros.addIdVendedor(vendedorElegido.getId());
        List<ItemMenu> menu;
        try {
            menu = itemsMenuDAO.filtrar(filtros);
        } catch (ItemNoEncontradoException e) {
            System.out.println("No se encontraron itemsMenus");
            return;
        }
        for (ItemMenu item : menu) {
            pedido.agregarItem(item, 1);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        PedidosDao pedidosDao = PedidosMemory.getInstance();
        pedidosDao.create(pedido);
        pedido.setEstado(ESTADO_PEDIDO.RECIBIDO);
        // Vendedor busca su pedido :p
        // Se le notifca al cliente que el pedido fue creado y esta EN_ENVIO
        try {
            List<Pedido> pedidosVendedor = pedidosDao.buscarPorEstado(ESTADO_PEDIDO.RECIBIDO, vendedorElegido);
            Pedido pEncontrado = pedidosVendedor.getFirst();
            pEncontrado.setEstado(ESTADO_PEDIDO.EN_ENVIO);
        } catch (PedidoNoEncontradoException e) {
            System.out.println("No se encontraron pedidos pendientes");
        }

        // El precio acumulado del pedido deberia ser igual a la de los itemsMenus del menu acumulados
        double pAcumulado = pedido.getPrecioAcumulado();
        double pAcumuladoEsperado = menu.stream().mapToDouble(ItemMenu::getPrecio).sum();
        System.out.println("Precio acumulado del pedido: " + pAcumulado+ "\n");
        System.out.println("Precio acumulado esperado: " + pAcumuladoEsperado + "\n");

        System.out.println("Pedido creado y en envio\n");
        // Cliente elige un metodo de pago
        System.out.println("Elige un metodo de pago");
        System.out.println("1. MercadoPago");
        System.out.println("2. Transferencia");


        // Se pide el input
        Scanner s = new Scanner(System.in);
        int input = s.nextInt();
        s.close();
        String alias = "maxi.bernard";
        String cbu = "1234567891234567894455";
        String cuit = "20-45954993-6";
        // Cliente llena los datos pertinentes al metodo de pago elegido

        switch (input) {
            case 1:
                System.out.println("Alias ingresado: " + alias);
                pedido.setEstrategiaDePago(new PagarConMercadoPago(alias));
                break;
            case 2:
                System.out.println("Cbu ingresado " + cbu);
                System.out.println("Cuit ingresado: " + cuit);
                pedido.setEstrategiaDePago(new PagarConTransferencia(cbu, cuit));
                break;
            default:
                System.out.println("Opcion invalida");
                return;
        }

        // Cliente paga :p
        pedido.pagar();
        double precioConRecargo = pedido.getPago().getPrecioTotalConRecargo();
        System.out.println("Precio total con recargo: " + precioConRecargo);
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
        ItemsMenuDAO itemsMenuDAO = ItemsMenuMemory.getInstance();
        for (ItemMenu item : itemsComidas) {
            itemsMenuDAO.create(item);
        }
        for (ItemMenu item : itemsBebidas) {
            itemsMenuDAO.create(item);
        }
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
