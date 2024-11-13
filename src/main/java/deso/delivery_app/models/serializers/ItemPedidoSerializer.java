package deso.delivery_app.models.serializers;

import deso.delivery_app.models.ItemPedido;

import java.util.Locale;

public class ItemPedidoSerializer implements ISerializable<ItemPedido>{
    @Override
    public String getInsertString(ItemPedido i) {
        return String.format(Locale.US,"INSERT INTO item_pedido (cantidad, precio_total, item_menu_id, pedido_id) VALUES (%d, %f, %d, %d)",
                i.getCantidad(), i.getPrecioTotal(), i.getItemMenu().getId(), i.getPedido().getId());
    }

    @Override
    public String getUpdateString(ItemPedido i) {
        return String.format(Locale.US,"UPDATE item_pedido SET cantidad=%d, precio_total=%f WHERE item_menu_id=%d, pedido_id=%d",
                i.getCantidad(), i.getPrecioTotal(), i.getItemMenu().getId(), i.getPedido().getId());
    }

    @Override
    public String getDeleteString(long id) {
        return String.format("DELETE FROM item_pedido WHERE id=%d", id);
    }

    @Override
    public String getSelectedString(long id) {
        return String.format("SELECT * FROM item_pedido WHERE id=%d", id);
    }

    @Override
    public String getSelectAllString() {
        return "SELECT * FROM item_pedido";
    }
}
