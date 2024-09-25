package deso.delivery_app.persistence.DAO;

import deso.delivery_app.Bebida;
import deso.delivery_app.ItemMenu;

import java.util.ArrayList;
import java.util.function.Predicate;

public class FiltrosItemMenu{
    private final ArrayList<Predicate<ItemMenu>> filtros = new ArrayList<>();

    public void addRangoPrecio(double precioMinimo, double precioMaximo) {
        addPrecioMinimo(precioMinimo);
        addPrecioMaximo(precioMaximo);
    }

    public void addPrecioMinimo(double precioMinimo) {
        filtros.add(i -> i.getPrecio() >= precioMinimo);
    }

    public void addPrecioMaximo(double precioMaximo) {
        filtros.add(i -> i.getPrecio() <= precioMaximo);
    }

    public void addComidaVegana() {
        filtros.add(ItemMenu::aptoVegano);
    }

    public void addComidaCeliaca() {
        filtros.add(ItemMenu::aptoCeliaco);
    }

    public void addNoAlcoholica() {
        filtros.add(i -> {
            // Si no es bebida , indica que es comida por lo tanto no es alcoholica
            if (!i.esBebida()) return true;
            Bebida b = (Bebida) i;
            return !b.esAlcoholica();
        });
    }

    public void addGaseosas() { filtros.add(ItemMenu::esBebida); }

    public void addComida() {
        filtros.add(ItemMenu::esComida);
    }

    public void addIdVendedor(long id) {
        filtros.add(i -> i.getVendedor().getId() == id);
    }

    public void addNombreVendedor(String nombre) {
        filtros.add(i -> i.getVendedor().getNombre().equals(nombre));
    }

    public void addCuitVendedor(String cuit) {
        filtros.add(i -> i.getVendedor().getCuit().equals(cuit));
    }

    public Predicate<ItemMenu> getFiltros() {
        return filtros.stream().reduce(item -> true, Predicate::and);
    }
}
