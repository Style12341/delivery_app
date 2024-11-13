package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Cliente;

import java.util.Locale;

public class ClienteSerializer implements ISerializable<Cliente> {

    @Override
    public String getInsertString(Cliente c) {
        return String.format(Locale.US,"INSERT INTO cliente (nombre, apellido, direccion, cuit, email, latitud, longitud) VALUES ('%s', '%s', '%s', '%s', '%s', %f, %f)",
                c.getNombre(), c.getApellido(), c.getDireccion(),c.getCuit(), c.getEmail(), c.getCoordenadas().getLat(), c.getCoordenadas().getLng());
    }

    @Override
    public String getUpdateString(Cliente c) {
        return String.format(Locale.US,"UPDATE cliente SET nombre='%s', apellido='%s', direccion='%s', cuit='%s', email='%s', latitud=%f, longitud=%f WHERE id=%d",
                c.getNombre(), c.getApellido(), c.getDireccion(), c.getCuit(), c.getEmail(), c.getCoordenadas().getLat(), c.getCoordenadas().getLng(), c.getId());
    }

    @Override
    public String getDeleteString(long id) {
        return String.format("DELETE FROM cliente WHERE id=%d", id);
    }

    @Override
    public String getSelectedString(long id) {
        return String.format("SELECT * FROM cliente WHERE id=%d", id);
    }

    @Override
    public String getSelectAllString() {
        return "SELECT * FROM cliente";
    }
}
