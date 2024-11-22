package deso.delivery_app.persistance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;

@Getter
@Entity
public class Comida extends ItemMenu {
    private static final Double FACTOR_PESO_POR_ENVASADO = 1.1;
    @Column
    private Double peso;

    public void setPeso(Double peso) {
        this.peso = peso * FACTOR_PESO_POR_ENVASADO;
    }
}
