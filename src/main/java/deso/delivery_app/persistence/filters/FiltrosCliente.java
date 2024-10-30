package deso.delivery_app.persistence.filters;

import deso.delivery_app.models.Cliente;

import java.util.ArrayList;
import java.util.function.Predicate;

public class FiltrosCliente {

    ArrayList<Predicate<Cliente>> filtros = new ArrayList<>();

    public Predicate<Cliente> getFiltros() {
        return filtros.stream().reduce(item -> true, Predicate::and);
    }

    public void addNombre(String nombre) {
        filtros.add(c -> c.getNombre().startsWith(nombre));
    }

    public void addApellido(String apellido) { filtros.add(c -> c.getApellido().startsWith(apellido)); }

    public void addEmail(String email){ filtros.add(c -> c.getEmail().startsWith(email)); }

    public void addDireccion(String direccion) {
        filtros.add(c -> c.getDireccion().startsWith(direccion));
    }
}
