package deso.delivery_app.persistance.models.composed_keys;

import deso.delivery_app.persistance.models.ItemMenu;
import deso.delivery_app.persistance.models.Pedido;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ItemPedidoKey implements Serializable {
    @OneToOne
    @JoinColumn(name = "item_menu_id", nullable = false)
    private ItemMenu itemMenu;
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
}
