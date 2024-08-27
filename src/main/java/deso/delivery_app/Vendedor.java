/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deso.delivery_app;

/**
 *
 * @author BMPC
 */
public class Vendedor {

    private int id;
    private String nombre;
    private String direccion;
    private Coordenada coordenadas;
    private static int nextId = 1;
    public Vendedor(String nombre, String direccion, Coordenada coordenadas) {
        this.id = nextId++;
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
    }
    public double distancia(Cliente c){
        return this.coordenadas.calcularDistancia(c.getCoordenadas());
    }
    @Override
    public String toString(){
        return "Vendedor " + id + ": " + nombre;
    }
    public boolean equalsId(int id){
        return this.id == id;
    }
    public boolean equalsNombre(String nombre){
        return this.nombre.equals(nombre);
    }
    /**
     * @return the id
     */
    public int getId() {
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
    

}
