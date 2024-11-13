package deso.delivery_app.models.serializers;

public interface ISerializable<T> {
    public String getInsertString(T t);
    public String getUpdateString(T t);
    public String getSelectedString(long id);
    public String getDeleteString(long id);
    public String getSelectAllString();
}
