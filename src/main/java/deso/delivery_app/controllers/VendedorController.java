package deso.delivery_app.controllers;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.factories.VendedorDAOFactory;
import deso.delivery_app.persistence.filters.FiltrosVendedor;
import deso.delivery_app.persistence.DAO.VendedorDAO;
import deso.delivery_app.persistence.memory.VendedorMemory;

import java.util.ArrayList;
import java.util.List;

public class VendedorController {
    VendedorDAO vendedorDAO = VendedorDAOFactory.getDAO();

    public List<Vendedor> getLista() {
        return getLista("", "");
    }

    public List<Vendedor> getLista(String nombre, String direccion) {
        FiltrosVendedor filters = new FiltrosVendedor();
        System.out.println("Nombre: " + nombre);
        System.out.println("Direccion: " + direccion);
        filters.addNombre(nombre);
        filters.addDireccion(direccion);
        List<Vendedor> vs = new ArrayList<Vendedor>();
        try {
            vs = vendedorDAO.filtrar(filters);
            for (Vendedor v : vs) {
                System.out.println(v);
            }
        } catch (ItemNoEncontradoException e) {
            //:P
        }
        return vs;
    }

    public Vendedor crear(Vendedor v) {
        return vendedorDAO.create(v);
    }

    public Vendedor modificar(Vendedor v) {
        return vendedorDAO.update(v);
    }

    public void eliminar(long id) {
        vendedorDAO.delete(id);
    }

    public Vendedor buscar(long id) {
        return vendedorDAO.get(id);
    }

}
