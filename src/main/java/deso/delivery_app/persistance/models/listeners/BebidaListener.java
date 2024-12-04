package deso.delivery_app.persistance.models.listeners;

import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.ItemPedido;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class BebidaListener {
    @PrePersist
    @PreUpdate
    public void setPeso(Bebida bebida) {
        bebida.setPeso(bebida.getPeso());
    }
}
