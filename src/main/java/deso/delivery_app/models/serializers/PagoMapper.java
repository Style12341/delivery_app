package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Pago;
import deso.delivery_app.persistence.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PagoMapper implements ISQLMapper<Pago> {
    private Connection conn = DBConnector.getConnection();
    @Override
    public PreparedStatement getInsertStatement(Pago p) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO pago (metodo_pago, fecha, precio_total_sin_recargo, precio_total_con_recargo) VALUES (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1,p.getStrategyStr());
        ps.setDate(2,new java.sql.Date(p.getFecha().getTime()));
        ps.setDouble(3,p.getPrecioTotalSinRecargo());
        ps.setDouble(4,p.getPrecioTotalConRecargo());
        return ps;
    }

    @Override
    public PreparedStatement getUpdateStatement(Pago p) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE pago SET metodo_pago=?, fecha=?, precio_total_sin_recargo=?, precio_total_con_recargo=? WHERE id=?");
        ps.setString(1,p.getStrategyStr());
        ps.setDate(2,new java.sql.Date(p.getFecha().getTime()));
        ps.setDouble(3,p.getPrecioTotalSinRecargo());
        ps.setDouble(4,p.getPrecioTotalConRecargo());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM pago WHERE id=?");
        ps.setLong(1,id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM pago WHERE id=?");
        ps.setLong(1,id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllStatement() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM pago");
        return ps;
    }

    @Override
    public List<Pago> deserialize(ResultSet rs) throws SQLException {
        return List.of();
    }
}
