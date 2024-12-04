package deso.delivery_app.persistance.models;

import deso.delivery_app.persistance.models.listeners.BebidaListener;
import deso.delivery_app.persistance.models.listeners.ItemPedidoListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@EntityListeners(BebidaListener.class)
public class Bebida extends ItemMenu {
    private static final double FACTOR_PESO_POR_ENVASADO = 1.2;
    private static final double FACTOR_PESO_PARA_ALCOHOL = 0.99;
    private static final double FACTOR_PESO_PARA_GASEOSA = 1.04;
    @Column
    private double volumen;
    @Column(name = "graduacion_alcoholica")
    private double graduacionAlcoholica;
    @Column(name = "es_alcoholica")
    private boolean esAlcoholica;
    @Column(name = "es_gaseosa")
    private boolean esGaseosa;

    @Override
    public Double getPeso() {
        double result = volumen;
        if (esAlcoholica) {
            result *= FACTOR_PESO_PARA_ALCOHOL;
        } else if (esGaseosa) {
            result *= FACTOR_PESO_PARA_GASEOSA;
        }
        return result * FACTOR_PESO_POR_ENVASADO;
    }

    public void setGraduacionAlcoholica(double graduacionAlcoholica) {
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.esAlcoholica = graduacionAlcoholica > 0;
    }
}
