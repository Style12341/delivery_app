package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.serializers.ClienteMapper;
import deso.delivery_app.persistence.DAO.ClienteDAO;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.persistence.filters.FiltrosCliente;
import deso.delivery_app.utils.Coordenada;

import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;

public class ClienteSQL implements ClienteDAO {
    ClienteMapper mapper = new ClienteMapper();
    Connection conn = DBConnector.getConnection();

    @Override
    public Cliente create(Cliente cliente) {
        try {
            PreparedStatement ps = mapper.getInsertStatement(cliente);
            int rowsAffected = ps.executeUpdate();
            long insertId = sqlUtils.fetchId(rowsAffected, ps);
            cliente.setId(insertId);
            return cliente;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cliente get(long id) {
        try {
            PreparedStatement ps = mapper.getSelectedStatement(id);
            ResultSet rs = ps.executeQuery();
            Cliente c = mapper.deserialize(rs).getFirst();
            return c;
        }catch (NoSuchElementException e){
            System.out.println("Cliente No Encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cliente update(Cliente cliente) {
        try {
            PreparedStatement ps = mapper.getUpdateStatement(cliente);
            ps.executeUpdate();
            return cliente;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement ps = mapper.getDeleteStatement(id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> filtrar(FiltrosCliente f) throws ItemNoEncontradoException {
        try {
            PreparedStatement ps = mapper.getSelectAllStatement();
            ResultSet rs = ps.executeQuery();
            List<Cliente> clientes = mapper.deserialize(rs);
            var filtros = f.getFiltros();
            List<Cliente> lista = clientes.stream().filter(filtros).toList();
            if (lista.isEmpty()) throw new ItemNoEncontradoException("Item no encontrado");
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cliente> buscarOrdenarPorNombre(FiltrosCliente f, Boolean descendente) throws ItemNoEncontradoException {
        List<Cliente> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        // CompareTo devuelve negativo si es menor y positivo si es mayor, para invertirlo se multiplica por -1
        return lista.stream().sorted((v1, v2) -> v2.getNombre().compareTo(v1.getNombre()) * desc).toList();

    }

    @Override
    public List<Cliente> buscarOrdenarPorProximidad(FiltrosCliente f, Coordenada coord, Boolean descendente) throws ItemNoEncontradoException {
        List<Cliente> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        return lista.stream().sorted((v1, v2) -> (int) (Double.compare(coord.calcularDistancia(v1.getCoordenadas()), coord.calcularDistancia(v2.getCoordenadas()))) * desc).toList();
    }
}
