package deso.delivery_app;

public class Plato extends ItemMenu {
    private static final double FACTOR_PESO_POR_ENVASADO = 1.1;
    private boolean esAptoVegano = false;
    private boolean esAptoCeliaco = false;
    private double peso;

    public Plato(String nombre, String descripcion, double precio, double peso, boolean vegano, boolean celiaco) {
        super(nombre, descripcion, precio, new Categoria("", TIPO_ITEM.COMIDA));
        this.precio = precio;
        this.esAptoVegano = vegano;
        this.esAptoCeliaco = celiaco;
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
