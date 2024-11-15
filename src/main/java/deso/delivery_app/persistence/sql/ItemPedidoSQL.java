package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.ItemPedido;
import deso.delivery_app.models.serializers.ClienteMapper;
import deso.delivery_app.models.serializers.ItemPedidoMapper;
import deso.delivery_app.persistence.DAO.ItemPedidoDAO;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.persistence.filters.FiltrosItemPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemPedidoSQL implements ItemPedidoDAO {
    ItemPedidoMapper mapper = new ItemPedidoMapper();
    Connection conn = DBConnector.getConnection();

    @Override
    public ItemPedido create(ItemPedido itemPedido) {
        try {
            PreparedStatement ps = mapper.getInsertStatement(itemPedido);
            int rowsAffected = ps.executeUpdate();
            long insertId = sqlUtils.fetchId(rowsAffected, ps);
            // itemPedido.setId(insertId); no tiene
            return itemPedido;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ItemPedido get(long idPedido, long idItemMenu) {
        try {
            PreparedStatement ps = mapper.getSelectedStatement(idPedido, idItemMenu);
            ResultSet rs = ps.executeQuery();
            ItemPedido c = mapper.deserialize(rs).getFirst();
            if (c == null) throw new ItemNoEncontradoException("ItemPedido no encontrado");
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ItemPedido update(ItemPedido itemPedido) {
        try {
            PreparedStatement ps = mapper.getUpdateStatement(itemPedido);
            ps.executeUpdate();
            return itemPedido;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(long idPedido, long idItemMenu) {
        try {
            PreparedStatement ps = mapper.getDeleteStatement(idPedido, idItemMenu);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ItemPedido> filtrar(FiltrosItemPedido f) throws ItemNoEncontradoException {
        try {
            PreparedStatement ps = mapper.getSelectAllStatement();
            ResultSet rs = ps.executeQuery();
            List<ItemPedido> itemsPedido = mapper.deserialize(rs);
            var filtros = f.getFiltros();
            List<ItemPedido> lista = itemsPedido.stream().filter(filtros).toList();
            if (lista.isEmpty()) throw new ItemNoEncontradoException("ItemPedido no encontrado");
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemPedido> buscarOrdenarPorNombre(FiltrosItemPedido f, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemPedido> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        if (lista.isEmpty()) throw new ItemNoEncontradoException("Item no encontrado");
        // CompareTo devuelve negativo si es menor y positivo si es mayor, para invertirlo se multiplica por -1
        return lista.stream().sorted((v1, v2) -> v2.getItemMenu().getNombre().compareTo(v1.getItemMenu().getNombre()) * desc).toList();
    }

    @Override
    public List<ItemPedido> buscarOrdenarPorPrecio(FiltrosItemPedido f, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemPedido> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        // ### TODO checkear si está bien
        return lista.stream().sorted((v1, v2) -> (int) (Double.compare(v1.getPrecioTotal(), v2.getPrecioTotal())) * desc).toList();
    }
}
