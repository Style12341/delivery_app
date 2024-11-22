package deso.delivery_app.persistance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
public class Bebida extends ItemMenu {
    private static final double FACTOR_PESO_POR_ENVASADO = 1.2;
    private static final double FACTOR_PESO_PARA_ALCOHOL = 0.99;
    private static final double FACTOR_PESO_PARA_GASEOSA = 1.04;
    @Column(nullable = false)
    private double volumen;
    @Column(name = "graduacion_alcoholica",nullable = false)
    private double graduacionAlcoholica;
    @Column(name = "es_alcoholica",nullable = false)
    private boolean esAlcoholica;
    @Column(name = "es_gaseosa", nullable = false)
    private boolean esGaseosa;

}
