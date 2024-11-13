package deso.delivery_app.models.serializers;

import deso.delivery_app.models.Pago;

import java.util.Locale;

public class PagoSerializer implements ISerializable<Pago> {

    @Override
    public String getInsertString(Pago p) {
        return String.format(Locale.US,"INSERT INTO Pago (metodo_pago, fecha, precio_total_sin_recargo, precio_total_con_recargo) VALUES ('%s', '%s', '%f', '%f')",
                p.getStrategyStr(), p.getFecha().toString(), p.getPrecioTotalSinRecargo(), p.getPrecioTotalConRecargo());
    }

    @Override
    public String getUpdateString(Pago p) {
        return String.format(Locale.US,"UPDATE Pago SET metodo_pago='%s', fecha='%s', precio_total_sin_recargo=%f, precio_total_con_recargo=%f WHERE id=%d",
                p.getStrategyStr(), p.getFecha().toString(), p.getPrecioTotalSinRecargo(), p.getPrecioTotalConRecargo(), p.getId());
    }

    @Override
    public String getDeleteString(long id) {
        return String.format("DELETE FROM Pago WHERE id=%d", id);
    }

    @Override
    public String getSelectedString(long id) {
        return String.format("SELECT * FROM Pago WHERE id=%d", id);
    }

    @Override
    public String getSelectAllString() {
        return "SELECT * FROM Pago";
    }
}
