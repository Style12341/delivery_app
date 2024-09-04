/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deso.delivery_app;

import java.io.Serializable;

/**
 * @author BMPC
 */
public class Cliente implements ISearcheable {

    private long id;
    private String nombre;
    private String cuit;
    private String email;
    private String direccion;
    private Coordenada coordenadas;
    private static long nextId = 0;

    public Cliente(String nombre, String cuit, String email, String direccion, Coordenada coordenadas) {
        this.id = nextId++;
        this.nombre = nombre;
        this.cuit = cuit;
        this.email = email;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
    }

    public String toString() {
        return "Cliente " + id + ": " + nombre;
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
     * @return the cuit
     */
    public String getCuit() {
        return cuit;
    }

    /**
     * @param cuit the cuit to set
     */
    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
