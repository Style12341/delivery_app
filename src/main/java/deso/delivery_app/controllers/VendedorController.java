package deso.delivery_app.controllers;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.persistence.DAO.FiltrosVendedor;
import deso.delivery_app.persistence.DAO.VendedorDao;
import deso.delivery_app.persistence.DAO.memory.VendedorMemory;
import deso.delivery_app.views.VendedoresIndexForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

public class VendedorController implements Controller {
    VendedorDao vendedorMemory = VendedorMemory.getInstance();
    private VendedoresIndexForm view;

    public VendedorController(VendedoresIndexForm view) {
        this.view = view;
        setUpViewEvents();
    }

    @Override
    public void mostrarLista(FiltrosVendedor filtros) {
        //proccessParamsFilters()
        List<Vendedor> vs = null;
        try {
            vs = vendedorMemory.filtrar(filtros);
            for (Vendedor v : vs) {
                System.out.println(v);
            }
        } catch (ItemNoEncontradoException e) {
            //:P
        }
        view.updateTable(vs);

    }

    @Override
    public void crear() {

    }

    @Override
    public void modificar() {

    }

    @Override
    public void eliminar(int id) {

    }

    @Override
    public void buscar(int id) {

    }


    private void setUpViewEvents() {
        view.getBuscarBtn().setAction(new AbstractAction("Buscar") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("Inside event handler for search button");
                // Extract fields and generate filters
                String nombre = view.getNombreField();
                String direccion = view.getDireccionField();
                FiltrosVendedor filters = new FiltrosVendedor();
                System.out.println("Nombre: " + nombre);
                System.out.println("Direccion: " + direccion);
                filters.addNombre(nombre);
                filters.addDireccion(direccion);
                mostrarLista(filters);
            }
        });

    }
}
