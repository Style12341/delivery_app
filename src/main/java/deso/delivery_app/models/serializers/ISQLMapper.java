package deso.delivery_app.models.serializers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ISQLMapper<T> {
    public PreparedStatement getInsertStatement(T t) throws SQLException;

    public PreparedStatement getUpdateStatement(T t) throws SQLException;

    public PreparedStatement getSelectedStatement(long id) throws SQLException;

    public PreparedStatement getDeleteStatement(long id) throws SQLException;

    public PreparedStatement getSelectAllStatement() throws SQLException;

    public List<T> deserialize(ResultSet rs) throws SQLException;
}
