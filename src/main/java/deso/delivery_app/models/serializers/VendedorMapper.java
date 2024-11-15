package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.utils.Coordenada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendedorMapper implements ISQLMapper<Vendedor> {
    Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertStatement(Vendedor v) throws SQLException {
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
    public PreparedStatement getUpdateStatement(Vendedor v) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE vendedor SET nombre=?, direccion=?, cuit=?, latitud=?, longitud=? WHERE id=?");
        ps.setString(1, v.getNombre());
        ps.setString(2, v.getDireccion());
        ps.setString(3, v.getCuit());
        Coordenada c = v.getCoordenadas();
        ps.setDouble(4,c.getLat());
        ps.setDouble(5,c.getLng());
        ps.setLong(6,v.getId());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM vendedor WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM vendedor WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllStatement() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * from vendedor");
        return ps;
    }

    @Override
    public List<Vendedor> deserialize(ResultSet rs) throws SQLException {
        List<Vendedor> vendedors = new ArrayList<>();
        while (rs.next()){
            long id = rs.getLong("id");
            double lat = rs.getDouble("latitud");
            double lng = rs.getDouble("longitud");
            String nombre = rs.getString("nombre");
            String cuit = rs.getString("cuit");
            String direccion = rs.getString("direccion");
            Coordenada c = new Coordenada(lat,lng);
            Vendedor v = new Vendedor(nombre,direccion,cuit,c);
            v.setId(id);
            vendedors.add(v);
        }
        return vendedors;
    }
}
