package deso.delivery_app.persistence.DAO;

import deso.delivery_app.Bebida;
import deso.delivery_app.ItemPedido;

import java.util.ArrayList;
import java.util.function.Predicate;

public class FiltrosItemPedido {
    private final ArrayList<Predicate<ItemPedido>> filtros = new ArrayList<>();

    public void setRangoPrecio(double precioMinimo, double precioMaximo) {
        setPrecioMinimo(precioMinimo);
        setPrecioMaximo(precioMaximo);
    }

    public void setPrecioMinimo(double precioMinimo) {
        filtros.add(i -> i.getItemMenu().getPrecio() >= precioMinimo);
    }

    public void setPrecioMaximo(double precioMaximo) {
        filtros.add(i -> i.getItemMenu().getPrecio() <= precioMaximo);
    }

    public void setComidaVegana() {
        filtros.add(i -> i.getItemMenu().aptoVegano());
    }

    public void setComidaCeliaca() {
        filtros.add(i -> i.getItemMenu().aptoCeliaco());
    }

    public void setBebidasNoAlcoholica() {
        filtros.add(i -> {
            // Si no es bebida , indica que es comida por lo tanto no es alcoholica
            if (!i.getItemMenu().esBebida()) return true;
            Bebida b = (Bebida) i.getItemMenu();
            return !b.esAlcoholica();
        });
    }

    public void setGaseosas() {
        filtros.add(i -> {
            if (!i.getItemMenu().esBebida()) return false;
            Bebida b = (Bebida) i.getItemMenu();
            return b.esGaseosa();
        });
    }

    public void setIdVendedor(long id) {
        filtros.add(i -> i.getPedido().getVendedor().getId() == id);
    }

    public void setCuitVendedor(String cuit) {
        filtros.add(i -> i.getPedido().getVendedor().getCuit().equals(cuit));
    }

    public void setIdCliente(long id) {
        filtros.add(i -> i.getPedido().getCliente().getId() == id);
    }

    public void setCuitCliente(String cuit) {
        filtros.add(i -> i.getPedido().getCliente().getCuit().equals(cuit));
    }

    public void setComida() {
        filtros.add(i -> i.getItemMenu().esComida());
    }

    public Predicate<ItemPedido> getFiltros() {
        return filtros.stream().reduce(item -> true, Predicate::and);
    }
}
