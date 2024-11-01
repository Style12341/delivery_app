package deso.delivery_app.persistence.filters;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.models.Pedido;

import java.util.ArrayList;
import java.util.function.Predicate;

public class FiltrosPedido {

    ArrayList<Predicate<Pedido>> filtros = new ArrayList<>();

    public Predicate<Pedido> getFiltros() {
        return filtros.stream().reduce(item -> true, Predicate::and);
    }

    public void addId(long id) {
        filtros.add(p -> p.getId() == id);
    }

    public void addEstado(ESTADO_PEDIDO estado) {
        filtros.add(p -> p.getEstado() == estado);
    }

    // Permite buscar por nombre o por apellido del cliente
    public void addNombreApellidoCliente(String nombreApellido) {
        filtros.add(p -> p.getCliente().getNombre().startsWith(nombreApellido) || p.getCliente().getApellido().startsWith(nombreApellido));
    }

    public void addNombreVendedor(String nombre) {
        filtros.add(p -> p.getVendedor().getNombre().startsWith(nombre));
    }

    public void addIdVendedor(long id) {
        filtros.add(p -> p.getVendedor().getId() == id);
    }

    public void addPrecioAcumulado(double precioMinimo, double precioMaximo) {
        filtros.add(p -> p.getPrecioAcumulado() >= precioMinimo);
        filtros.add(p -> p.getPrecioAcumulado() <= precioMaximo);
    }
}
