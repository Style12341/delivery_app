package deso.delivery_app.models.serializers;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.persistence.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

public class PedidoSerializer implements ISerializable<Pedido> {
    Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertString(Pedido p) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO pedido (cliente_id, vendedor_id, pago_id, precio_acumulado, estado) VALUES (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setLong(1, p.getCliente().getId());
        ps.setLong(2, p.getVendedor().getId());
        ps.setLong(3, p.getPago().getId());
        ps.setDouble(4, p.getPrecioAcumulado());
        ps.setString(5, p.getEstado().toString());
        return ps;
    }

    @Override
    public PreparedStatement getUpdateString(Pedido p) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE pedido SET cliente_id=?, vendedor_id=?, pago_id=?, precio_acumulado=?, estado=? WHERE id=?");
        ps.setLong(1, p.getCliente().getId());
        ps.setLong(2, p.getVendedor().getId());
        ps.setLong(3, p.getPago().getId());
        ps.setDouble(4, p.getPrecioAcumulado());
        ps.setString(5, p.getEstado().toString());
        ps.setLong(6, p.getId());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteString(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM pedido WHERE id=?");
        ps.setLong(1,id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedString(long id) throws SQLException{
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM pedido WHERE id=?");
        ps.setLong(1,id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllString() throws SQLException{
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM pedido");
        return ps;
    }
}
