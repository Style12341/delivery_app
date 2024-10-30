package deso.delivery_app.controllers;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.FiltrosVendedor;
import deso.delivery_app.persistence.DAO.VendedorDao;
import deso.delivery_app.persistence.DAO.memory.VendedorMemory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class VendedorController {
    VendedorDao vendedorMemory = VendedorMemory.getInstance();

    public List<Vendedor> getLista(String nombre, String direccion) {
        FiltrosVendedor filters = new FiltrosVendedor();
        System.out.println("Nombre: " + nombre);
        System.out.println("Direccion: " + direccion);
        filters.addNombre(nombre);
        filters.addDireccion(direccion);
        List<Vendedor> vs = new ArrayList<Vendedor>();
        try {
            vs = vendedorMemory.filtrar(filters);
            for (Vendedor v : vs) {
                System.out.println(v);
            }
        } catch (ItemNoEncontradoException e) {
            //:P
        }
        return vs;
    }

    public void crear() {

    }

    public void modificar() {

    }


    public void eliminar(int id) {

    }


    public void buscar(int id) {

    }


}
