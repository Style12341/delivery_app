package deso.delivery_app;

import java.util.ArrayList;

/**
 * @author BMPC
 */
public class Vendedor implements ISearcheable {

    private long id;
    private String nombre;
    private String direccion;
    private Coordenada coordenadas;
    private static long nextId = 0;
    private ArrayList<ItemMenu> menu;

    public Vendedor(String nombre, String direccion, Coordenada coordenadas) {
        this.id = nextId++;
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
    }

    public double distancia(Cliente c) {
        return this.coordenadas.calcularDistancia(c.getCoordenadas());
    }

    @Override
    public String toString() {
        return "Vendedor " + id + ": " + nombre;
    }

    @Override
    public boolean equalsId(long id) {
        return this.id == id;
    }

    @Override
    public boolean equalsNombre(String nombre) {
        return this.nombre.equals(nombre);
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the coordenadas
     */
    public Coordenada getCoordenadas() {
        return coordenadas;
    }

    /**
     * @param coordenadas the coordenadas to set
     */
    public void setCoordenadas(Coordenada coordenadas) {
        this.coordenadas = coordenadas;
    }

    public ArrayList<Bebida> getBebidas() {
        ArrayList<Bebida> bebidas = new ArrayList<>();
        for (ItemMenu item : menu) {
            if (item.esBebida()) {
                bebidas.add((Bebida) item);
            }
        }
        return bebidas;
    }

    public ArrayList<Plato> getComidas() {
        ArrayList<Plato> platos = new ArrayList<>();
        for (ItemMenu item : menu) {
            if (item.esComida()) {
                platos.add((Plato) item);
            }
        }
        return platos;
    }

    public ArrayList<Plato> getComidasVeganas() {
        ArrayList<Plato> platos = getComidas();
        platos.removeIf(p -> !p.aptoVegano());
        return platos;
    }

    public ArrayList<Bebida> getBebidasSinAlcohol() {
        ArrayList<Bebida> bebidas = getBebidas();
        bebidas.removeIf(b -> b.esAlcoholica());
        return bebidas;
    }
}
