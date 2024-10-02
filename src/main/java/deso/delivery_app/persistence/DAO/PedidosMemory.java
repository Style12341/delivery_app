package deso.delivery_app.persistence.DAO;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.ItemPedido;
import deso.delivery_app.Pedido;
import deso.delivery_app.Vendedor;
import deso.delivery_app.exception.PedidoNoEncontradoException;

import java.util.ArrayList;
import java.util.List;

public class PedidosMemory implements PedidosDao{
    ArrayList<Pedido> pedidos = new ArrayList<>();

    private static PedidosMemory SINGLETON_INSTANCE;

    private PedidosMemory() {
        pedidos = new ArrayList<>();
    }

    public static PedidosMemory getInstance() {
        if (SINGLETON_INSTANCE == null) SINGLETON_INSTANCE = new PedidosMemory();
        return SINGLETON_INSTANCE;
    }

    @Override
    public Pedido create(Pedido pedido) {
        pedidos.add(pedido);
        return pedido;
    }

    @Override
    public Pedido get(long id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    @Override
    public Pedido update(Pedido pedido) {
        for (Pedido p : pedidos) {
            if (p.getId() == pedido.getId()) {
                p = pedido;
                return p;
            }
        }
        return pedido;
    }

    @Override
    public void delete(long id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                pedidos.remove(pedido);
                return;
            }
        }
    }

    @Override
    public List<Pedido> buscarPorEstado(ESTADO_PEDIDO estado, Vendedor vendedor) throws PedidoNoEncontradoException {
        List<Pedido> pedidosEstado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() == estado && pedido.getVendedor() == vendedor) {
                pedidosEstado.add(pedido);
            }
        }
        if (pedidosEstado.isEmpty()) {
            throw new PedidoNoEncontradoException("No se encontraron pedidos con el estado " + estado);
        }
        return pedidosEstado;
    }
}
