package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Categoria;
import deso.delivery_app.persistence.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoriaSerializer implements ISerializable<Categoria> {
    Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertString(Categoria c) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO categoria (descripcion, tipo_item) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,c.getDescripcion());
        ps.setString(2,c.getTipoItem().toString());
        return ps;
    }

    @Override
    public PreparedStatement getUpdateString(Categoria c) throws SQLException  {
        PreparedStatement ps = conn.prepareStatement("UPDATE categoria SET descripcion=?, tipo_item=? WHERE id=?");
        ps.setString(1,c.getDescripcion());
        ps.setString(2,c.getTipoItem().toString());
        ps.setLong(3,c.getId());
        return ps;
    }

    @Override
    public PreparedStatement getDeleteString(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM categoria WHERE id=?");
        ps.setLong(1,id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedString(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria WHERE id=?");
        ps.setLong(1,id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllString() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria");
        return ps;
    }
}
