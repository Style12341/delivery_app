package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Vendedor;

import java.util.Locale;

public class VendedorSerializer implements ISerializable<Vendedor> {

    @Override
    public String getInsertString(Vendedor v) {
        return String.format(Locale.US,"INSERT INTO vendedor (nombre, direccion, cuit, latitud, longitud) VALUES ('%s', '%s', '%s', %f, %f)",
                v.getNombre(), v.getDireccion(), v.getCuit(), v.getCoordenadas().getLat(),v.getCoordenadas().getLng());
    }

    @Override
    public String getUpdateString(Vendedor v) {
        return String.format(Locale.US,"UPDATE vendedor SET nombre='%s', direccion='%s', cuit='%s', latitud=%f, longitud=%f WHERE id=%d",
                v.getNombre(), v.getDireccion(), v.getCuit(), v.getCoordenadas().getLat(),v.getCoordenadas().getLng(), v.getId());
    }

    @Override
    public String getDeleteString(long id) {
        return String.format("DELETE FROM vendedor WHERE id=%d", id);
    }

    @Override
    public String getSelectedString(long id) {
        return String.format("SELECT * FROM vendedor WHERE id=%d", id);
    }

    @Override
    public String getSelectAllString() {
        return "SELECT * FROM vendedor";
    }
}
