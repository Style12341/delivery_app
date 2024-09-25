package deso.delivery_app.persistence.DAO;

import deso.delivery_app.Bebida;
import deso.delivery_app.ItemPedido;

import java.util.ArrayList;
import java.util.function.Predicate;

public class FiltrosItemPedido {
    private final ArrayList<Predicate<ItemPedido>> filtros = new ArrayList<>();

    public void addRangoPrecio(double precioMinimo, double precioMaximo) {
        addPrecioMinimo(precioMinimo);
        addPrecioMaximo(precioMaximo);
    }

    public void addPrecioMinimo(double precioMinimo) {
        filtros.add(i -> i.getItemMenu().getPrecio() >= precioMinimo);
    }

    public void addPrecioMaximo(double precioMaximo) {
        filtros.add(i -> i.getItemMenu().getPrecio() <= precioMaximo);
    }

    public void addComidaVegana() {
        filtros.add(i -> i.getItemMenu().aptoVegano());
    }

    public void addComidaCeliaca() {
        filtros.add(i -> i.getItemMenu().aptoCeliaco());
    }

    public void addNoAlcoholica() {
        filtros.add(i -> {
            // Si no es bebida , indica que es comida por lo tanto no es alcoholica
            if (!i.getItemMenu().esBebida()) return true;
            Bebida b = (Bebida) i.getItemMenu();
            return !b.esAlcoholica();
        });
    }

    public void addGaseosas() { filtros.add(i -> i.getItemMenu().esBebida()); }

    public void addComida() {
        filtros.add(i -> i.getItemMenu().esComida());
    }

    public void addIdVendedor(long id) {
        filtros.add(i -> i.getPedido().getVendedor().getId() == id);
    }

    public void addNombreVendedor(String nombre) {
        filtros.add(i -> i.getPedido().getVendedor().getNombre().equals(nombre));
    }

    public void addCuitVendedor(String cuit) {
        filtros.add(i -> i.getPedido().getVendedor().getCuit().equals(cuit));
    }

    public void addIdCliente(long id) {
        filtros.add(i -> i.getPedido().getCliente().getId() == id);
    }

    public void addCuitCliente(String cuit) {
        filtros.add(i -> i.getPedido().getCliente().getCuit().equals(cuit));
    }

    public void addEmailCliente(String email) {
        filtros.add(i -> i.getPedido().getCliente().getEmail().equals(email));
    }

    public void addApellidoCliente(String apellido) {
        filtros.add(i -> i.getPedido().getCliente().getApellido().equals(apellido));
    }

    public Predicate<ItemPedido> getFiltros() {
        return filtros.stream().reduce(item -> true, Predicate::and);
    }
}
