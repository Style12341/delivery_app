package deso.delivery_app;

import deso.delivery_app.persistence.DAO.ItemsPedidoDao;
import deso.delivery_app.persistence.DAO.ItemsPedidoMemory;
import deso.delivery_app.utils.Pair;

import java.util.ArrayList;

public class Pedido {
    private long id;
    private Cliente cliente;
    private Vendedor vendedor;
    private static long NEXT_ID = 0;
    private final ArrayList<ItemPedido> detallePedido = new ArrayList<>();

    public Pedido(Vendedor vendedor, Cliente cliente, ArrayList<Pair<ItemMenu, Integer>> items) {
        this.id = NEXT_ID++;
        this.vendedor = vendedor;
        this.cliente = cliente;
        for (Pair<ItemMenu, Integer> item : items) {
            agregarItem(item.first, item.second);
        }
    }

    private void agregarItem(ItemMenu item, Integer cantidad) {
        ItemPedido i = new ItemPedido(cantidad, item, this);
        ItemsPedidoDao itemsPedidoDao = ItemsPedidoMemory.getInstance();
        itemsPedidoDao.create(i);
        detallePedido.add(i);
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
