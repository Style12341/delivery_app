package deso.delivery_app.persistence.filters;

import deso.delivery_app.models.Vendedor;

import java.util.ArrayList;
import java.util.function.Predicate;

public class FiltrosVendedor {
    ArrayList<Predicate<Vendedor>> filtros = new ArrayList<>();

    public Predicate<Vendedor> getFiltros() {
        return filtros.stream().reduce(item -> true, Predicate::and);
    }

    public void addNombre(String nombre) {
        filtros.add(v -> v.getNombre().startsWith(nombre));
    }

    public void addDireccion(String direccion) {
        filtros.add(v -> v.getDireccion().startsWith(direccion));
    }
}
