package deso.delivery_app.persistence.sql;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.exception.PedidoNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.models.serializers.ClienteMapper;
import deso.delivery_app.models.serializers.PedidoMapper;
import deso.delivery_app.persistence.DAO.PedidoDAO;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.persistence.filters.FiltrosPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoSQL implements PedidoDAO {
    PedidoMapper mapper = new PedidoMapper();
    Connection conn = DBConnector.getConnection();

    @Override
    public Pedido create(Pedido pedido) {
        try {
            PreparedStatement ps = mapper.getInsertStatement(pedido);
            int rowsAffected = ps.executeUpdate();
            long insertId = sqlUtils.fetchId(rowsAffected, ps);
            pedido.setId(insertId);
            return pedido;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pedido get(long id) {
        try {
            PreparedStatement ps = mapper.getSelectedStatement(id);
            ResultSet rs = ps.executeQuery();
            Pedido c = mapper.deserialize(rs).getFirst();
            if (c == null) throw new ItemNoEncontradoException("Pedido no encontrado");
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pedido update(Pedido pedido) {
        try {
            PreparedStatement ps = mapper.getUpdateStatement(pedido);
            ps.executeUpdate();
            return pedido;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement ps = mapper.getDeleteStatement(id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pedido> buscarPorEstado(ESTADO_PEDIDO estado, Vendedor vendedor) throws PedidoNoEncontradoException {
        List<Pedido> pedidosEstado = new ArrayList<>();
        try {
            ResultSet rs = mapper.getSelectAllStatement().executeQuery();
            List<Pedido> pedidos = mapper.deserialize(rs);
            for (Pedido pedido : pedidos) {
                if (pedido.getEstado() == estado && pedido.getVendedor() == vendedor) {
                    pedidosEstado.add(pedido);
                }
            }
            if (pedidosEstado.isEmpty()) {
                throw new PedidoNoEncontradoException("No se encontraron pedidos con el estado " + estado);
            }
            return pedidosEstado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pedido> filtrar(FiltrosPedido f) throws PedidoNoEncontradoException {
        try {
            PreparedStatement ps = mapper.getSelectAllStatement();
            ResultSet rs = ps.executeQuery();
            List<Pedido> pedidos = mapper.deserialize(rs);
            var filtros = f.getFiltros();
            List<Pedido> lista = pedidos.stream().filter(filtros).toList();
            if (lista.isEmpty()) throw new PedidoNoEncontradoException("Pedido no encontrado");
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
