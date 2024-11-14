package deso.delivery_app.models.serializers;

import deso.delivery_app.models.ItemPedido;
import deso.delivery_app.persistence.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

public class ItemPedidoSerializer implements ISerializable<ItemPedido>{
    private Connection conn = DBConnector.getConnection();
    @Override
    public PreparedStatement getInsertString(ItemPedido i) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO item_pedido (cantidad, precio_total, item_menu_id, pedido_id) VALUES (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, i.getCantidad());
        ps.setDouble(2, i.getPrecioTotal());
        ps.setLong(3,i.getItemMenu().getId());
        ps.setLong(4,i.getPedido().getId());
        return ps;
    }

    @Override
    public PreparedStatement getUpdateString(ItemPedido i)throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE item_pedido SET cantidad=?, precio_total=? WHERE item_menu_id=? and pedido_id=?");
        ps.setInt(1, i.getCantidad());
        ps.setDouble(2, i.getPrecioTotal());
        ps.setLong(3,i.getItemMenu().getId());
        ps.setLong(4,i.getPedido().getId());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteString(long id)throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public PreparedStatement getDeleteString(long idPedido, long idItemMenu)throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM item_pedido WHERE item_menu_id=? and pedido_id=?");
        ps.setLong(1, idItemMenu);
        ps.setLong(2,idPedido);
        return ps;
    }
    @Override
    public PreparedStatement getSelectedString(long id)throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public PreparedStatement getSelectedString(long idPedido, long idItemMenu)throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM item_pedido WHERE item_menu_id=? and pedido_id=?");
        ps.setLong(1, idItemMenu);
        ps.setLong(2,idPedido);
        return ps;
    }
    @Override
    public PreparedStatement getSelectAllString()throws SQLException {
        return conn.prepareStatement("SELECT * FROM item_pedido");
    }

    public PreparedStatement getSelectAllFromPedido(long idPedido) throws SQLException{
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM item_pedido WHERE pedido_id=?");
        ps.setLong(1,idPedido);
        return ps;
    }
}
