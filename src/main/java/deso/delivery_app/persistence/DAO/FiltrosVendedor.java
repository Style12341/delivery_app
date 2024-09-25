package deso.delivery_app.persistence.DAO;

import deso.delivery_app.Vendedor;

import java.util.ArrayList;
import java.util.function.Predicate;

public class FiltrosVendedor {
    ArrayList<Predicate<Vendedor>> filtros = new ArrayList<>();

    Predicate<Vendedor> getFiltros() {
        return filtros.stream().reduce(item -> true, Predicate::and);
    }

    void addNombre(String nombre) {
        filtros.add(v -> v.getNombre().equals(nombre));
    }

    void addDireccion(String direccion) {
        filtros.add(v -> v.getDireccion().equals(direccion));
    }
}
