package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.utils.Coordenada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class VendedorSerializer implements ISerializable<Vendedor> {
    Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertString(Vendedor v) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO vendedor (nombre, direccion, cuit, latitud, longitud) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,v.getNombre());
        ps.setString(2,v.getDireccion());
        ps.setString(3,v.getCuit());
        Coordenada c = v.getCoordenadas();
        ps.setDouble(4,c.getLat());
        ps.setDouble(5,c.getLng());
        return ps;
    }

    @Override
    public PreparedStatement getUpdateString(Vendedor v) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE vendedor SET nombre=?, direccion=?, cuit=?, latitud=?, longitud=? WHERE id=?");
        ps.setString(1, v.getNombre());
        ps.setString(2, v.getDireccion());
        ps.setString(3, v.getCuit());
        Coordenada c = v.getCoordenadas();
        ps.setDouble(4,c.getLat());
        ps.setDouble(5,c.getLng());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteString(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM vendedor WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedString(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM vendedor WHRE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllString() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * from vendedor");
        return ps;
    }
}
