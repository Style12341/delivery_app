package deso.delivery_app.models.serializers;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.Pago;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.utils.Coordenada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoMapper implements ISQLMapper<Pedido> {
    Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertStatement(Pedido p) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO pedido (cliente_id, vendedor_id, pago_id, precio_acumulado, estado) VALUES (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setLong(1, p.getCliente().getId());
        ps.setLong(2, p.getVendedor().getId());
        if (p.getPago() != null)
            ps.setLong(3, p.getPago().getId());
        else
            ps.setNull(3, java.sql.Types.BIGINT);
        ps.setDouble(4, p.getPrecioAcumulado());
        ps.setString(5, p.getEstado().toString());
        return ps;
    }

    @Override
    public PreparedStatement getUpdateStatement(Pedido p) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE pedido SET cliente_id=?, vendedor_id=?, pago_id=?, precio_acumulado=?, estado=? WHERE id=?");
        ps.setLong(1, p.getCliente().getId());
        ps.setLong(2, p.getVendedor().getId());
        if (p.getPago() != null)
            ps.setLong(3, p.getPago().getId());
        else
            ps.setNull(3, java.sql.Types.BIGINT);
        ps.setDouble(4, p.getPrecioAcumulado());
        ps.setString(5, p.getEstado().toString());
        ps.setLong(6, p.getId());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM pedido WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM pedido JOIN vendedor ON pedido.vendedor_id = vendedor.id JOIN cliente on pedido.cliente_id = cliente.id LEFT JOIN pago on pedido.pago_id = pago.id WHERE pedido.id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllStatement() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM pedido JOIN vendedor ON pedido.vendedor_id = vendedor.id JOIN cliente on pedido.cliente_id = cliente.id LEFT JOIN pago on pedido.pago_id = pago.id");
        return ps;
    }

    @Override
    public List<Pedido> deserialize(ResultSet rs) throws SQLException {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        while (rs.next()) {
            long id = rs.getLong("id");
            double precioTotal = rs.getDouble("precio_acumulado");
            Cliente c = deserializeCliente(rs);
            Vendedor v = deserializeVendedor(rs);
            Pago p = deserializePago(rs);
            Pedido pedido = new Pedido(v, c);
            System.out.println("Pedido: " + pedido);
            ESTADO_PEDIDO estado = ESTADO_PEDIDO.valueOf(rs.getString("pedido.estado"));
            pedido.setPago(p);
            pedido.setId(id);
            pedido.setPrecioAcumulado(precioTotal);
            pedido.setEstado(estado);
            pedidos.add(pedido);
        }
        return pedidos;
    }

    private Cliente deserializeCliente(ResultSet rs) throws SQLException {
        long id = rs.getLong("cliente.id");
        String nombre = rs.getString("cliente.nombre");
        String apellido = rs.getString("cliente.apellido");
        String email = rs.getString("cliente.email");
        String cuit = rs.getString("cliente.cuit");
        String direccion = rs.getString("cliente.direccion");
        double latitud = rs.getDouble("cliente.latitud");
        double longitud = rs.getDouble("cliente.longitud");
        Coordenada coord = new Coordenada(latitud, longitud);
        Cliente cliente = new Cliente(nombre, apellido, cuit, email, direccion, coord);
        cliente.setId(id);
        return cliente;
    }

    private Vendedor deserializeVendedor(ResultSet rs) throws SQLException {
        long id = rs.getLong("vendedor.id");
        String nombre = rs.getString("vendedor.nombre");
        String direccion = rs.getString("vendedor.direccion");
        String cuit = rs.getString("vendedor.cuit");
        double latitud = rs.getDouble("vendedor.latitud");
        double longitud = rs.getDouble("vendedor.longitud");
        Coordenada coord = new Coordenada(latitud, longitud);
        Vendedor vendedor = new Vendedor(nombre, direccion, cuit, coord);
        vendedor.setId(id);
        return vendedor;
    }

    private Pago deserializePago(ResultSet rs) throws SQLException {
        long id = rs.getLong("pago.id");
        if (id == 0) return null;
        double precioTotalSinRecargo = rs.getDouble("pago.precio_total_sin_recargo");
        double precioTotalConRecargo = rs.getDouble("pago.precio_total_con_recargo");
        Date fecha = rs.getDate("pago.fecha");
        String tipo = rs.getString("pago.tipo");
        Pago pago = new Pago(precioTotalSinRecargo, precioTotalConRecargo, fecha, tipo);
        pago.setId(id);
        return pago;
    }
}
