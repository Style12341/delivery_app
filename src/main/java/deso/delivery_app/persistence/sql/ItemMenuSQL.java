package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.serializers.ClienteMapper;
import deso.delivery_app.models.serializers.ItemMenuMapper;
import deso.delivery_app.persistence.DAO.ItemMenuDAO;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.persistence.filters.FiltrosItemMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemMenuSQL implements ItemMenuDAO {
    ItemMenuMapper mapper = new ItemMenuMapper();
    Connection conn = DBConnector.getConnection();

    @Override
    public ItemMenu create(ItemMenu itemMenu) {
        try {
            PreparedStatement ps = mapper.getInsertStatement(itemMenu);
            int rowsAffected = ps.executeUpdate();
            long insertId = sqlUtils.fetchId(rowsAffected, ps);
            itemMenu.setId(insertId);
            return itemMenu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ItemMenu get(long id){
        try {
            PreparedStatement ps = mapper.getSelectedStatement(id);
            ResultSet rs = ps.executeQuery();
            ItemMenu itemMenu = mapper.deserialize(rs).getFirst();
            if (itemMenu == null) throw new ItemNoEncontradoException("itemMenu no encontrado");
            return itemMenu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ItemMenu update(ItemMenu itemMenu) {
        try {
            PreparedStatement ps = mapper.getUpdateStatement(itemMenu);
            ps.executeUpdate();
            return itemMenu;
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
    public List<ItemMenu> filtrar(FiltrosItemMenu f) throws ItemNoEncontradoException {
        try {
            PreparedStatement ps = mapper.getSelectAllStatement();
            ResultSet rs = ps.executeQuery();
            List<ItemMenu> itemsMenu = mapper.deserialize(rs);
            var filtros = f.getFiltros();
            List<ItemMenu> lista = itemsMenu.stream().filter(filtros).toList();
            if (lista.isEmpty()) throw new ItemNoEncontradoException("Item no encontrado");
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemMenu> buscarOrdenarPorNombre(FiltrosItemMenu f, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemMenu> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        if (lista.isEmpty()) throw new ItemNoEncontradoException("Item no encontrado");
        // CompareTo devuelve negativo si es menor y positivo si es mayor, para invertirlo se multiplica por -1
        return lista.stream().sorted((v1, v2) -> v2.getNombre().compareTo(v1.getNombre()) * desc).toList();
    }

    @Override
    public List<ItemMenu> buscarOrdenarPorPrecio(FiltrosItemMenu f, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemMenu> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        // ### TODO checkear si está bien
        return lista.stream().sorted((v1, v2) -> (int) (Double.compare(v1.getPrecio(), v2.getPrecio())) * desc).toList();
    }
}
