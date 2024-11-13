package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.Pedido;

import java.util.Locale;

public class PedidoSerializer implements ISerializable<Pedido> {
    @Override
    public String getInsertString(Pedido p) {
        return String.format(Locale.US,"INSERT INTO Pedido (cliente_id, vendedor_id, pago_id, precio_acumulado, estado) VALUES (%d, %d, %d, %f, '%s')",
                p.getCliente().getId(), p.getVendedor().getId(), p.getPago().getId(), p.getPrecioAcumulado(), p.getEstado().toString());
    }

    @Override
    public String getUpdateString(Pedido p) {
        return String.format(Locale.US,"UPDATE Pedido SET cliente_id=%d, vendedor_id=%d, pago_id=%d, precio_acumulado=%f, estado='%s' WHERE id=%d",
                p.getCliente().getId(), p.getVendedor().getId(), p.getPago().getId(), p.getPrecioAcumulado(), p.getEstado().toString(), p.getId());
    }

    @Override
    public String getDeleteString(long id) {
        return String.format("DELETE FROM Pedido WHERE id=%d", id);
    }

    @Override
    public String getSelectedString(long id) {
        return String.format("SELECT * FROM Pedido WHERE id=%d", id);
    }

    @Override
    public String getSelectAllString() {
        return "SELECT * FROM Pedido";
    }
}
