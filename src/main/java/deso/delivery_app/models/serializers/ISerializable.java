package deso.delivery_app.models.serializers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ISerializable<T> {
    public PreparedStatement getInsertString(T t) throws SQLException;

    public PreparedStatement getUpdateString(T t) throws SQLException;

    public PreparedStatement getSelectedString(long id) throws SQLException;

    public PreparedStatement getDeleteString(long id) throws SQLException;

    public PreparedStatement getSelectAllString() throws SQLException;
}
