package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.serializers.ClienteSerializer;
import deso.delivery_app.persistence.DAO.ClienteDAO;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.persistence.filters.FiltrosCliente;
import deso.delivery_app.utils.Coordenada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ClienteSQL implements ClienteDAO {
    ClienteSerializer serializer = new ClienteSerializer();
    Connection conn = DBConnector.getConnection();

    @Override
    public Cliente create(Cliente cliente) {
        String query = serializer.getInsertString(cliente);
        try {
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Retrieve the auto-generated keys (insert ID)
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int insertId = generatedKeys.getInt(1);
                    cliente.setId(insertId);
                    return cliente;
                } else {
                    System.out.println("Failed to retrieve client ID.");
                }
            } else {
                System.out.println("No client inserted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cliente get(long id) {

        String query = serializer.getSelectedString(id);
        try {
            Statement stmt = DBConnector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String cuit = rs.getString("cuit");
                String direccion = rs.getString("direccion");
                double latitud = rs.getDouble("latitud");
                double longitud = rs.getDouble("longitud");
                Coordenada coord = new Coordenada(latitud, longitud);
                return new Cliente(nombre, apellido, cuit, email, direccion, coord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cliente update(Cliente cliente) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Cliente> filtrar(FiltrosCliente f) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<Cliente> buscarOrdenarPorNombre(FiltrosCliente f, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }

    @Override
    public List<Cliente> buscarOrdenarPorProximidad(FiltrosCliente f, Coordenada coord, Boolean descendente) throws ItemNoEncontradoException {
        return List.of();
    }
}
