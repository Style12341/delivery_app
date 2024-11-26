package deso.delivery_app.seeds;

import deso.delivery_app.controllers.ClienteController;
import deso.delivery_app.controllers.ItemMenuController;
import deso.delivery_app.controllers.PedidoController;
import deso.delivery_app.enums.TIPO_ITEM;
import deso.delivery_app.persistance.models.*;
import deso.delivery_app.persistance.models.composed_keys.ItemPedidoKey;
import deso.delivery_app.persistance.repository.*;
import deso.delivery_app.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Seeder implements CommandLineRunner {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    VendedorRepository vendedorRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    BebidaRepository bebidaRepository;
    @Autowired
    ComidaRepository comidaRepository;
    @Autowired
    ItemPedidoRepository itemPedidoRepository;
    List<Comida> itemsComidas = new ArrayList<>();
    List<Bebida> itemsBebidas = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();
    List<Vendedor> vendedores = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        //Only if development

        loadSeedsFromFile();
    }

    public void loadSeedsFromFile() {
        createItems();
        createClientes();
        createVendedores();
        createPedidos();
    }

    private void createVendedores() {
        Vendedor v1 = new Vendedor();
        v1.setNombre("La Dominga");
        v1.setDireccion("Herndarias 833");
        v1.setCuit("27-40727599-8");
        v1.setCoordenada(new Coordenada(-40.0, -63.0));
        Vendedor v2 = new Vendedor();
        v2.setNombre("Lo de Mario");
        v2.setDireccion("General Paz 6000");
        v2.setCuit("26-13787921-6");
        v2.setCoordenada(new Coordenada(-40.5, -63.5));
        vendedores.add(vendedorRepository.save(v1));
        vendedores.add(vendedorRepository.save(v2));
        for (int i = 0; i < itemsBebidas.size() / 2; i++) {
            Bebida b = itemsBebidas.get(i);
            b.setVendedor(v1);
            itemsBebidas.set(i, bebidaRepository.save(b));
        }
        for (int i = 0; i < itemsComidas.size() / 2; i++) {
            Comida c = itemsComidas.get(i);
            c.setVendedor(v1);
            itemsComidas.set(i, comidaRepository.save(c));
        }
        // Vendedor 2
        for (int i = itemsBebidas.size() / 2; i < itemsBebidas.size(); i++) {
            Bebida b = itemsBebidas.get(i);
            b.setVendedor(v2);
            itemsBebidas.set(i, bebidaRepository.save(b));
        }
        for (int i = itemsComidas.size() / 2; i < itemsComidas.size(); i++) {
            Comida c = itemsComidas.get(i);
            c.setVendedor(v2);
            itemsComidas.set(i, comidaRepository.save(c));
        }


    }

    private void createItems() {
        // Comidas
        Object[][] data = {
                {"Ensalada Mixta", "Lechuga, tomate, cebolla, zanahoria", 5.99, 300.0, true, true, TIPO_ITEM.COMIDA},
                {"Milanesa con papas fritas", "Milanesa de carne con papas fritas", 9.50, 450.0, false, false, TIPO_ITEM.COMIDA},
                {"Pizza Margarita", "Pizza con queso, tomate y albahaca", 8.25, 400.0, false, false, TIPO_ITEM.COMIDA},
                {"Tarta de espinaca", "Tarta con relleno de espinaca y ricota", 6.80, 350.0, true, false, TIPO_ITEM.COMIDA},
                {"Risotto de hongos", "Risotto cremoso con champiñones y queso parmesano", 10.75, 350.0, false, true, TIPO_ITEM.COMIDA},
                {"Wrap de falafel", "Wrap de garbanzos, hummus, y vegetales frescos", 7.99, 300.0, true, false, TIPO_ITEM.COMIDA},
                {"Coca-Cola", "Bebida gaseosa clásica", 2.50, 500.0, 0.0, true, true, TIPO_ITEM.BEBIDA},
                {"Cerveza Corona", "Cerveza rubia ligera", 3.99, 355.0, 4.5, false, false, TIPO_ITEM.BEBIDA},
                {"Agua Mineral", "Agua embotellada sin gas", 1.50, 600.0, 0.0, false, true, TIPO_ITEM.BEBIDA},
                {"Vino Tinto", "Vino tinto de la casa", 8.50, 750.0, 13.5, false, true, TIPO_ITEM.BEBIDA},
                {"Fanta Naranja", "Bebida gaseosa con sabor a naranja", 2.80, 500.0, 0.0, true, true, TIPO_ITEM.BEBIDA},
                {"Gin Tonic", "Gin mezclado con agua tónica y una rodaja de limón", 6.50, 400.0, 12.0, true, true, TIPO_ITEM.BEBIDA},
                {"Jugo de naranja", "Jugo natural exprimido de naranjas frescas", 3.0, 350.0, 0.0, false, true, TIPO_ITEM.BEBIDA}
        };
        for (Object[] item : data) {
            if (item[6] == TIPO_ITEM.COMIDA) {
                Comida comida = new Comida();
                comida.setNombre((String) item[0]);
                comida.setDescripcion((String) item[1]);
                comida.setPrecio((Double) item[2]);
                comida.setPeso((Double) item[3]);
                comida.setEsAptoCeliaco((Boolean) item[4]);
                comida.setEsAptoVegano((Boolean) item[5]);
                comida.setCategoria(TIPO_ITEM.COMIDA);
                itemsComidas.add(comida);
            } else {
                Bebida bebida = new Bebida();
                bebida.setNombre((String) item[0]);
                bebida.setDescripcion((String) item[1]);
                bebida.setPrecio((Double) item[2]);
                bebida.setVolumen((Double) item[3]);
                bebida.setGraduacionAlcoholica((Double) item[4]);
                bebida.setEsAptoCeliaco((Boolean) item[5]);
                bebida.setEsAptoVegano((Boolean) item[6]);
                bebida.setCategoria(TIPO_ITEM.BEBIDA);
                itemsBebidas.add(bebida);
            }
        }


    }

    private void createClientes() {
        // Cliente 1
        Cliente c1 = new Cliente();
        //"Pepito", "Perez", "27-28033214-8", "pepe@test.com", "Herndarias 836", new Coordenada(-40, -63.5)
        c1.setNombre("Pepito");
        c1.setApellido("Perez");
        c1.setCuit("27-28033214-8");
        c1.setEmail("pepe@test.com");
        c1.setDireccion("Herndarias 836");
        c1.setCoordenada(new Coordenada(-40.0, -63.5));
        Cliente c2 = new Cliente();
        // "Marito", "Ledesma", "27-28033414-8", "mario@test.com", "General Paz 6002", new Coordenada(-40.5, -64)
        c2.setNombre("Marito");
        c2.setApellido("Ledesma");
        c2.setCuit("27-28033414-8");
        c2.setEmail("mario@test.com");
        c2.setDireccion("General Paz 6002");
        c2.setCoordenada(new Coordenada(-40.5, -64.0));
        clientes.add(clienteRepository.save(c1));
        clientes.add(clienteRepository.save(c2));
    }

    private void createPedidos() {
        // Pedido 1
        Pedido p1 = new Pedido();
        p1.setVendedor(vendedores.getFirst());
        p1.setCliente(clientes.getFirst());
        List<ItemPedido> itemsPedido1 = new ArrayList<>();
        ItemPedido ip1 = new ItemPedido();
        ip1.setCantidad(2);
        ItemPedidoKey ipk1 = new ItemPedidoKey();
        ipk1.setPedido(p1);
        ipk1.setItemMenu(itemsComidas.getFirst());
        ip1.setId(ipk1);
        itemsPedido1.add(ip1);
        ItemPedido ip2 = new ItemPedido();
        ip2.setCantidad(1);
        ItemPedidoKey ipk2 = new ItemPedidoKey();
        ipk2.setPedido(p1);
        ipk2.setItemMenu(itemsBebidas.getFirst());
        ip2.setId(ipk2);
        itemsPedido1.add(ip2);
        p1.setItems(itemsPedido1);
        pedidoRepository.save(p1);
        // Pedido 2
        Pedido p2 = new Pedido();
        p2.setVendedor(vendedores.get(1));
        p2.setCliente(clientes.get(1));
        List<ItemPedido> itemsPedido2 = new ArrayList<>();
        ItemPedido ip3 = new ItemPedido();
        ip3.setCantidad(1);
        ItemPedidoKey ipk3 = new ItemPedidoKey();
        ipk3.setPedido(p2);
        ipk3.setItemMenu(itemsComidas.get(3));
        ip3.setId(ipk3);
        itemsPedido2.add(ip3);
        ItemPedido ip4 = new ItemPedido();
        ip4.setCantidad(3);
        ItemPedidoKey ipk4 = new ItemPedidoKey();
        ipk4.setPedido(p2);
        ipk4.setItemMenu(itemsBebidas.get(6));
        ip4.setId(ipk4);
        itemsPedido2.add(ip4);
        p2.setItems(itemsPedido2);
        pedidoRepository.save(p2);

        // Pedido 3
        Pedido p3 = new Pedido();
        p3.setVendedor(vendedores.get(1));
        p3.setCliente(clientes.getFirst());
        List<ItemPedido> itemsPedido3 = new ArrayList<>();
        ItemPedido ip5 = new ItemPedido();
        ip5.setCantidad(1);
        ItemPedidoKey ipk5 = new ItemPedidoKey();
        ipk5.setPedido(p3);
        ipk5.setItemMenu(itemsComidas.get(5));
        ip5.setId(ipk5);
        itemsPedido3.add(ip5);
        ItemPedido ip6 = new ItemPedido();
        ip6.setCantidad(2);
        ItemPedidoKey ipk6 = new ItemPedidoKey();
        ipk6.setPedido(p3);
        ipk6.setItemMenu(itemsBebidas.get(4));
        ip6.setId(ipk6);
        itemsPedido3.add(ip6);
        p3.setItems(itemsPedido3);
        pedidoRepository.save(p3);

        // Pedido 4
        Pedido p4 = new Pedido();
        p4.setVendedor(vendedores.getFirst());
        p4.setCliente(clientes.get(1));
        List<ItemPedido> itemsPedido4 = new ArrayList<>();
        ItemPedido ip7 = new ItemPedido();
        ip7.setCantidad(2);
        ItemPedidoKey ipk7 = new ItemPedidoKey();
        ipk7.setPedido(p4);
        ipk7.setItemMenu(itemsComidas.get(4));
        ip7.setId(ipk7);
        itemsPedido4.add(ip7);
        ItemPedido ip8 = new ItemPedido();
        ip8.setCantidad(1);
        ItemPedidoKey ipk8 = new ItemPedidoKey();
        ipk8.setPedido(p4);
        ipk8.setItemMenu(itemsBebidas.get(5));
        ip8.setId(ipk8);
        itemsPedido4.add(ip8);
        p4.setItems(itemsPedido4);
        pedidoRepository.save(p4);

        // Pedido 5
        Pedido p5 = new Pedido();
        p5.setVendedor(vendedores.getFirst());
        p5.setCliente(clientes.getFirst());
        List<ItemPedido> itemsPedido5 = new ArrayList<>();
        ItemPedido ip9 = new ItemPedido();
        ip9.setCantidad(1);
        ItemPedidoKey ipk9 = new ItemPedidoKey();
        ipk9.setPedido(p5);
        ipk9.setItemMenu(itemsComidas.get(1));
        ip9.setId(ipk9);
        itemsPedido5.add(ip9);
        ItemPedido ip10 = new ItemPedido();
        ip10.setCantidad(2);
        ItemPedidoKey ipk10 = new ItemPedidoKey();
        ipk10.setPedido(p5);
        ipk10.setItemMenu(itemsBebidas.getFirst());
        ip10.setId(ipk10);
        itemsPedido5.add(ip10);
        p5.setItems(itemsPedido5);
        pedidoRepository.save(p5);
        itemPedidoRepository.saveAll(itemsPedido1);
        itemPedidoRepository.saveAll(itemsPedido2);
        itemPedidoRepository.saveAll(itemsPedido3);
        itemPedidoRepository.saveAll(itemsPedido4);
        itemPedidoRepository.saveAll(itemsPedido5);

    }
}
