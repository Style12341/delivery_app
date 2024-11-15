package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Cliente;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.utils.Coordenada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteMapper implements ISQLMapper<Cliente> {
    private Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertStatement(Cliente c) throws SQLException {
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
    public PreparedStatement getUpdateStatement(Cliente c) throws SQLException {
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
    public PreparedStatement getDeleteStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM cliente WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM cliente WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllStatement() throws SQLException {
        return conn.prepareStatement("SELECT * FROM cliente");
    }

    public List<Cliente> deserialize(ResultSet rs) throws SQLException {
        List<Cliente> clientes = new ArrayList<Cliente>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String email = rs.getString("email");
            String cuit = rs.getString("cuit");
            String direccion = rs.getString("direccion");
            double latitud = rs.getDouble("latitud");
            double longitud = rs.getDouble("longitud");
            Coordenada coord = new Coordenada(latitud, longitud);
            Cliente cliente = new Cliente(nombre, apellido, cuit, email, direccion, coord);
            cliente.setId(id);
            clientes.add(cliente);
        }
        return clientes;
    }
}
