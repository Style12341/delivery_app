package deso.delivery_app.persistance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@SQLDelete(sql = "UPDATE item_menu SET deleted_at = now() WHERE id = ?")
public class Comida extends ItemMenu {
    private static final Double FACTOR_PESO_POR_ENVASADO = 1.1;

    public void setPeso(Double peso) {
        this.peso = peso * FACTOR_PESO_POR_ENVASADO;
    }
}
