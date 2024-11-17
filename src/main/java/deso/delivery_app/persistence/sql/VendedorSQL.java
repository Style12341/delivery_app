package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.models.serializers.ClienteMapper;
import deso.delivery_app.models.serializers.VendedorMapper;
import deso.delivery_app.persistence.DAO.VendedorDAO;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.persistence.filters.FiltrosVendedor;
import deso.delivery_app.utils.Coordenada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class VendedorSQL implements VendedorDAO {
    VendedorMapper mapper = new VendedorMapper();
    Connection conn = DBConnector.getConnection();

    @Override
    public Vendedor create(Vendedor vendedor) {
        try {
            PreparedStatement ps = mapper.getInsertStatement(vendedor);
            int rowsAffected = ps.executeUpdate();
            long insertId = sqlUtils.fetchId(rowsAffected, ps);
            vendedor.setId(insertId);
            return vendedor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Vendedor get(long id) {
        try {
            PreparedStatement ps = mapper.getSelectedStatement(id);
            ResultSet rs = ps.executeQuery();
            Vendedor c = mapper.deserialize(rs).stream().findFirst().orElseThrow();
            return c;
        }catch (NoSuchElementException e) {
            System.out.println("Vendedor no encontrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Vendedor update(Vendedor vendedor) {
        try {
            PreparedStatement ps = mapper.getUpdateStatement(vendedor);
            ps.executeUpdate();
            return vendedor;
        }
        catch (Exception e) {
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
    public List<Vendedor> filtrar(FiltrosVendedor f) throws ItemNoEncontradoException{
        try {
            PreparedStatement ps = mapper.getSelectAllStatement();
            ResultSet rs = ps.executeQuery();
            List<Vendedor> clientes = mapper.deserialize(rs);
            var filtros = f.getFiltros();
            List<Vendedor> lista = clientes.stream().filter(filtros).toList();
            if (lista.isEmpty()) throw new ItemNoEncontradoException("Vendedor no encontrado");
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vendedor> buscarOrdenarPorNombre(FiltrosVendedor f, Boolean descendente) throws ItemNoEncontradoException {
        List<Vendedor> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        // CompareTo devuelve negativo si es menor y positivo si es mayor, para invertirlo se multiplica por -1
        return lista.stream().sorted((v1, v2) -> v2.getNombre().compareTo(v1.getNombre()) * desc).toList();
    }

    @Override
    public List<Vendedor> buscarOrdenarPorProximidad(FiltrosVendedor f, Coordenada coord, Boolean descendente) throws ItemNoEncontradoException {
        List<Vendedor> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        return lista.stream().sorted((v1, v2) -> (int) (Double.compare(coord.calcularDistancia(v1.getCoordenadas()), coord.calcularDistancia(v2.getCoordenadas()))) * desc).toList();
    }
}
