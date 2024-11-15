package deso.delivery_app.models.serializers;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.models.*;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.utils.Coordenada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoMapper implements ISQLMapper<ItemPedido> {
    private Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertStatement(ItemPedido i) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO item_pedido (cantidad, precio_total, item_menu_id, pedido_id) VALUES (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, i.getCantidad());
        ps.setDouble(2, i.getPrecioTotal());
        ps.setLong(3, i.getItemMenu().getId());
        ps.setLong(4, i.getPedido().getId());
        return ps;
    }

    @Override
    public PreparedStatement getUpdateStatement(ItemPedido i) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE item_pedido SET cantidad=?, precio_total=? WHERE item_menu_id=? and pedido_id=?");
        ps.setInt(1, i.getCantidad());
        ps.setDouble(2, i.getPrecioTotal());
        ps.setLong(3, i.getItemMenu().getId());
        ps.setLong(4, i.getPedido().getId());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteStatement(long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PreparedStatement getDeleteStatement(long idPedido, long idItemMenu) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM item_pedido WHERE item_menu_id=? and pedido_id=?");
        ps.setLong(1, idItemMenu);
        ps.setLong(2, idPedido);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedStatement(long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PreparedStatement getSelectedStatement(long idPedido, long idItemMenu) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM item_pedido JOIN pedido ON item_pedido.pedido_id = pedido.id  JOIN item_menu ON item_pedido.item_menu_id = item_menu.id JOIN vendedor ON pedido.vendedor_id = vendedor.id JOIN cliente ON pedido.cliente_id = cliente.id WHERE item_menu_id=? and pedido_id=?");
        ps.setLong(1, idItemMenu);
        ps.setLong(2, idPedido);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllStatement() throws SQLException {
        return conn.prepareStatement("SELECT * FROM item_pedido JOIN pedido ON item_pedido.pedido_id = pedido.id  JOIN item_menu ON item_pedido.item_menu_id = item_menu.id JOIN vendedor ON pedido.vendedor_id = vendedor.id JOIN cliente ON pedido.cliente_id = cliente.id ");
    }

    public PreparedStatement getSelectAllFromPedido(long idPedido) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM item_pedido JOIN pedido ON item_pedido.pedido_id = pedido.id  JOIN item_menu ON item_pedido.item_menu_id = item_menu.id JOIN vendedor ON pedido.vendedor_id = vendedor.id JOIN cliente ON pedido.cliente_id = cliente.id WHERE pedido_id=?");
        ps.setLong(1, idPedido);
        return ps;
    }

    @Override
    public List<ItemPedido> deserialize(ResultSet rs) throws SQLException {
        List<ItemPedido> items = new ArrayList<>();
        while (rs.next()) {
            ItemMenu im = deserializeItemMenu(rs);
            Cliente c = deserializeCliente(rs);
            Vendedor v = deserializeVendedor(rs);
            Pedido p = deserializePedido(rs);
            p.setVendedor(v);
            p.setCliente(c);
            int cantidad = rs.getInt("item_pedido.cantidad");
            double precioTotal = rs.getDouble("item_pedido.precio_total");
            ItemPedido ip = new ItemPedido(cantidad, im, p);
            ip.setPrecioTotal(precioTotal);
            items.add(ip);
        }
        return items;
    }

    private Cliente deserializeCliente(ResultSet rs) throws SQLException {
        long id = rs.getLong("cliente.id");
        String nombre = rs.getString("cliente.nombre");
        String apellido = rs.getString("cliente.apellido");
        String email = rs.getString("cliente.email");
        String cuit = rs.getString("cliente.cuit");
        String direccion = rs.getString("cliente.direccion");
        Coordenada coordenada = new Coordenada(rs.getDouble("cliente.latitud"), rs.getDouble("cliente.longitud"));
        Cliente c = new Cliente(nombre, apellido, cuit,email , direccion, coordenada);
        c.setId(id);
        return c;
    }

    private Vendedor deserializeVendedor(ResultSet rs) throws SQLException {
        long id = rs.getLong("vendedor.id");
        String nombre = rs.getString("vendedor.nombre");
        String cuit = rs.getString("vendedor.cuit");
        String direccion = rs.getString("vendedor.direccion");
        Coordenada coordenada = new Coordenada(rs.getDouble("vendedor.latitud"), rs.getDouble("vendedor.longitud"));
        Vendedor v = new Vendedor(nombre, direccion, cuit, coordenada);
        v.setId(id);
        return v;
    }

    private ItemMenu deserializeItemMenu(ResultSet rs) throws SQLException {
        TIPO_ITEM tipoItem = TIPO_ITEM.valueOf(rs.getString("item_menu.tipo"));
        String nombre = rs.getString("item_menu.nombre");
        double precio = rs.getDouble("item_menu.precio");
        String descripcion = rs.getString("item_menu.descripcion");
        boolean aptoVegano = rs.getInt("item_menu.apto_vegano") != 0;
        boolean aptoCeliaco = rs.getInt("item_menu.apto_celiaco") != 0;
        long id = rs.getLong("item_menu.id");
        Vendedor v = null;
        Categoria c = null;
        switch (tipoItem) {
            case BEBIDA:
                boolean gaseosa = rs.getInt("item_menu.gaseosa") != 0;
                boolean alcoholica = rs.getInt("item_menu.alcoholica") != 0;
                double gradoAlcohol = rs.getDouble("item_menu.graduacion_alcoholica");
                double volumen = rs.getDouble("item_menu.volumen");
                Bebida b = new Bebida(nombre, descripcion, precio, volumen, gradoAlcohol, gaseosa, alcoholica);
                b.setVegano(aptoVegano);
                b.setCeliaco(aptoCeliaco);
                b.setId(id);
                return b;
            case COMIDA:
                double peso = rs.getDouble("item_menu.peso");
                Plato com = new Plato(nombre, descripcion, precio, peso, aptoVegano, aptoCeliaco);
                com.setId(id);
                return com;
        }
        throw new SQLException("Tipo de item no reconocido");
    }

    private Pedido deserializePedido(ResultSet rs) throws SQLException {
        long id = rs.getLong("pedido.id");
        double precioAcumulado = rs.getDouble("pedido.precio_acumulado");
        ESTADO_PEDIDO estado = ESTADO_PEDIDO.valueOf(rs.getString("pedido.estado"));
        Pedido p = new Pedido();
        p.setEstado(estado);
        p.setId(id);

        return p;
    }
}
