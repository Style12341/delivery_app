package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Categoria;

public class CategoriaSerializer implements ISerializable<Categoria> {

    @Override
    public String getInsertString(Categoria c) {
        return String.format("INSERT INTO Categoria (descripcion, tipo_item) VALUES ('%s', '%s')",
                c.getDescripcion(), c.getTipoItem().toString());
    }

    @Override
    public String getUpdateString(Categoria c) {
        return String.format("UPDATE Categoria SET descripcion='%s', tipo_item='%s' WHERE id=%d",
                c.getDescripcion(), c.getTipoItem().toString(), c.getId());
    }

    @Override
    public String getDeleteString(long id) {
        return String.format("DELETE FROM Categoria WHERE id=%d", id);
    }

    @Override
    public String getSelectedString(long id) {
        return String.format("SELECT * FROM Categoria WHERE id=%d", id);
    }

    @Override
    public String getSelectAllString() {
        return "SELECT * FROM Categoria";
    }
}
