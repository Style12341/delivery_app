package deso.delivery_app.models.serializers;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.models.Categoria;
import deso.delivery_app.persistence.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaMapper implements ISQLMapper<Categoria> {
    Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertStatement(Categoria c) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO categoria (descripcion, tipo_item) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getDescripcion());
        ps.setString(2, c.getTipoItem().toString());
        return ps;
    }

    @Override
    public PreparedStatement getUpdateStatement(Categoria c) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE categoria SET descripcion=?, tipo_item=? WHERE id=?");
        ps.setString(1, c.getDescripcion());
        ps.setString(2, c.getTipoItem().toString());
        ps.setLong(3, c.getId());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM categoria WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllStatement() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria");
        return ps;
    }

    public List<Categoria> deserialize(ResultSet rs) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String descripcion = rs.getString("descripcion");
            TIPO_ITEM tipoItem = TIPO_ITEM.valueOf(rs.getString("tipo_item"));
            Categoria c = new Categoria(descripcion, tipoItem);
            c.setId(id);
            categorias.add(c);
        }
        return categorias;
    }
}
