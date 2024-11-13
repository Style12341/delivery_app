package deso.delivery_app.models;

import deso.delivery_app.TIPO_ITEM;

public class Plato extends ItemMenu {
    private static final double FACTOR_PESO_POR_ENVASADO = 1.1;
    private double peso;

    public Plato(String nombre, String descripcion, double precio, double peso, boolean vegano, boolean celiaco, Categoria c) {
        super(nombre, descripcion, precio, c);
        this.peso = peso;
        this.esAptoVegano = vegano;
        this.esAptoCeliaco = celiaco;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    @Override
    public double peso() {
        return peso * FACTOR_PESO_POR_ENVASADO;
    }

    @Override
    public boolean esComida() {
        return true;
    }

    @Override
    public boolean esBebida() {
        return false;
    }

    @Override
    public boolean aptoVegano() {
        return esAptoVegano;
    }

    public boolean aptoCeliaco() {
        return esAptoCeliaco;
    }
}
