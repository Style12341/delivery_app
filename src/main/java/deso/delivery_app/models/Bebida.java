package deso.delivery_app.models;

import deso.delivery_app.TIPO_ITEM;

public class Bebida extends ItemMenu {
    private static final double FACTOR_PESO_POR_ENVASADO = 1.2;
    private static final double FACTOR_PESO_PARA_ALCOHOL = 0.99;
    private static final double FACTOR_PESO_PARA_GASEOSA = 1.04;
    private double volumen;
    private double graduacionAlcoholica;
    private boolean esAlcoholica;
    private boolean esGaseosa;


    public Bebida(String nombre, String descripcion, double precio, double volumen, double graduacionAlcoholica, boolean esGaseosa, boolean esAptoCeliaco, Categoria c) {
        super(nombre, descripcion, precio, c);
        this.volumen = volumen;
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.esAlcoholica = graduacionAlcoholica > 0;
        this.esGaseosa = esGaseosa;
        this.esAptoCeliaco = esAptoCeliaco;
        this.esAptoVegano = true;
    }

    @Override
    public double peso() {
        double res = volumen;
        if (esAlcoholica) {
            res *= FACTOR_PESO_PARA_ALCOHOL;
        } else if (esGaseosa) {
            res *= FACTOR_PESO_PARA_GASEOSA;
        }
        return res * FACTOR_PESO_POR_ENVASADO;
    }

    @Override
    public boolean esComida() {
        return false;
    }

    @Override
    public boolean esBebida() {
        return true;
    }

    @Override
    public boolean aptoVegano() {
        return esAptoVegano; // xd
    }

    @Override
    public boolean aptoCeliaco() {
        return esAptoCeliaco; // xd
    }


    public double getVolumen() {
        return volumen;
    }

    public double getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }

    public boolean esAlcoholica() {
        return esAlcoholica;
    }

    public boolean esGaseosa() {
        return esGaseosa;
    }

    public void setGraduacionAlcoholica(double graduacionAlcoholicaField) {
        this.graduacionAlcoholica = graduacionAlcoholicaField;
    }

    public void setGaseosa(boolean esGaseosaField) {
        this.esGaseosa = esGaseosaField;
    }

    public void setVolumen(double volumenField) {
        this.volumen = volumenField;
    }
}
