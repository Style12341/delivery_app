package deso.delivery_app.controllers;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.ClienteDAO;
import deso.delivery_app.persistence.DAO.PedidoDAO;
import deso.delivery_app.persistence.DAO.VendedorDAO;
import deso.delivery_app.persistence.DAO.factories.ClienteDAOFactory;
import deso.delivery_app.persistence.DAO.factories.PedidoDAOFactory;
import deso.delivery_app.persistence.DAO.factories.VendedorDAOFactory;
import deso.delivery_app.persistence.filters.FiltrosPedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoController {
    PedidoDAO pedidoDAO = PedidoDAOFactory.getDAO();
    VendedorDAO vendedorDAO = VendedorDAOFactory.getDAO();
    ClienteDAO clienteDAO = ClienteDAOFactory.getDAO();
    public static final int NO_FILTRAR_POR_ID = -1;
    public static final int NO_FILTRAR_VENDEDOR = -1;


    public List<Pedido> getLista() {
        return getLista(NO_FILTRAR_POR_ID, ESTADO_PEDIDO.TODOS, NO_FILTRAR_VENDEDOR, "", "", 0, Double.MAX_VALUE);
    }

    public List<Pedido> getLista(long id, ESTADO_PEDIDO estado, long id_vendedor, String vendedor, String cliente, double precioMinimo, double precioMaximo) {
        FiltrosPedido filters = new FiltrosPedido();
        if (id != NO_FILTRAR_POR_ID) filters.addId(id);
        switch (estado) {
            case RECIBIDO:
                filters.addEstado(ESTADO_PEDIDO.RECIBIDO);
                break;
            case ACEPTADO:
                filters.addEstado(ESTADO_PEDIDO.ACEPTADO);
                break;
            case PREPARADO:
                filters.addEstado(ESTADO_PEDIDO.PREPARADO);
                break;
            case ENVIADO:
                filters.addEstado(ESTADO_PEDIDO.ENVIADO);
                break;
        }
        if (id_vendedor != NO_FILTRAR_VENDEDOR) filters.addIdVendedor(id_vendedor);
        filters.addNombreVendedor(vendedor);
        filters.addNombreApellidoCliente(cliente);
        filters.addPrecioAcumulado(precioMinimo, precioMaximo);
        List<Pedido> ps = new ArrayList<Pedido>();
        try {
            ps = pedidoDAO.filtrar(filters);
        } catch (Exception e) {
            //:P
        }
        return ps;
    }

    public void crear(Pedido p, long idVendedor, long idCliente) {
        Vendedor v = vendedorDAO.get(idVendedor);
        Cliente c = clienteDAO.get(idCliente);
        p.setVendedor(v);
        p.setCliente(c);
        v.addPedido(p);
        c.addPedido(p);
        pedidoDAO.create(p);
    }

    public void modificar(Pedido p) {
        pedidoDAO.update(p);
    }

    public void eliminar(long id) {
        pedidoDAO.delete(id);
    }

    public Pedido buscar(long id) {
        return pedidoDAO.get(id);
    }

    public Pedido cambiarEstado(Pedido pedido, ESTADO_PEDIDO estado) {
        pedido.setEstado(estado);
        return pedidoDAO.update(pedido);
    }
}
