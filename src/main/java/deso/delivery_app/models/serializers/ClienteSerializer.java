package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Cliente;
import deso.delivery_app.utils.Coordenada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

public class ClienteSerializer implements ISerializable<Cliente> {
    private Connection conn;

    @Override
    public PreparedStatement getInsertString(Cliente c) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO cliente (nombre, apellido, direccion, cuit, email, latitud, longitud) VALUES (?,?,?,?,?,?,?) ", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getApellido());
        ps.setString(3, c.getDireccion());
        ps.setString(4, c.getCuit());
        ps.setString(5, c.getEmail());
        Coordenada coord = c.getCoordenadas();
        ps.setDouble(6, coord.getLat());
        ps.setDouble(7, coord.getLng());
        return ps;
    }

    @Override
    public PreparedStatement getUpdateString(Cliente c) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE cliente SET nombre=?, apellido=?, direccion=?, cuit=?, email=?, latitud=?, longitud=? WHERE id=?");
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getApellido());
        ps.setString(3, c.getDireccion());
        ps.setString(4, c.getCuit());
        ps.setString(5, c.getEmail());
        Coordenada coord = c.getCoordenadas();
        ps.setDouble(6, coord.getLat());
        ps.setDouble(7, coord.getLng());
        ps.setLong(8, c.getId());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteString(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM cliente WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedString(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM cliente WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllString() throws SQLException {
        return conn.prepareStatement("SELECT * FROM cliente");
    }
}
