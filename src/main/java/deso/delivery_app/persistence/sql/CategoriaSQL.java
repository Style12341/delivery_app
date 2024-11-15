package deso.delivery_app.persistence.sql;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Categoria;
import deso.delivery_app.models.serializers.CategoriaMapper;
import deso.delivery_app.persistence.DAO.CategoriaDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CategoriaSQL implements CategoriaDAO {
    CategoriaMapper mapper = new CategoriaMapper();

    @Override
    public Categoria create(Categoria p) {
        try {
            PreparedStatement ps = mapper.getInsertStatement(p);
            int rowsAffected = ps.executeUpdate();
            long insertId = sqlUtils.fetchId(rowsAffected, ps);
            p.setId(insertId);
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Categoria get(long id) {
        try {
            PreparedStatement ps = mapper.getSelectedStatement(id);
            ResultSet rs = ps.executeQuery();
            Categoria c = mapper.deserialize(rs).getFirst();
            if (c == null) throw new ItemNoEncontradoException("Categoria no encontrada");
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Categoria update(Categoria p) {
        try {
            PreparedStatement ps = mapper.getUpdateStatement(p);
            ps.executeUpdate();
            return p;
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
    public Categoria getCategoriaByTipoItem(TIPO_ITEM tipo_item) {
        try {
            PreparedStatement ps = mapper.getSelectAllStatement();
            ResultSet rs = ps.executeQuery();
            Categoria c = mapper.deserialize(rs).stream().filter(cat -> cat.getTipoItem().equals(tipo_item)).findFirst().orElse(null);
            return c;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
