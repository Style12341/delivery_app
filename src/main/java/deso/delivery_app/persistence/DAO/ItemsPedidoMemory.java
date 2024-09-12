package deso.delivery_app.persistence.DAO;

import deso.delivery_app.Bebida;
import deso.delivery_app.ItemPedido;
import deso.delivery_app.exception.ItemNoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//singleton

public class ItemsPedidoMemory implements ItemsPedidoDao {

    private static ItemsPedidoMemory SINGLETON_INSTANCE;
    private final List<ItemPedido> listaItemsPedido;

    private ItemsPedidoMemory() {
        listaItemsPedido = new ArrayList<ItemPedido>();
    }

    public ItemsPedidoMemory getItemsPedidoMemory() {
        if (SINGLETON_INSTANCE == null) SINGLETON_INSTANCE = new ItemsPedidoMemory();
        return SINGLETON_INSTANCE;
    }

    @Override
    public ItemPedido create(ItemPedido itemPedido) {
        listaItemsPedido.add(itemPedido);
        return itemPedido;
    }

    @Override
    public void delete(long id) {
        Optional<ItemPedido> item = listaItemsPedido.stream().filter(i -> i.getId() == id).findFirst();
        if (item.isPresent()) {
            boolean remove = listaItemsPedido.remove(item.get());
        }
    }

    @Override
    public List<ItemPedido> filtrar(CondicionesFiltradoItemPedido c) throws ItemNoEncontradoException {
        // Si es comida o bebida ->
        List<ItemPedido> lista = listaItemsPedido.stream().filter(i -> {
            //filtroEsComida
            if (!c.esFiltraComida()) return true;
            return i.getItemMenu().esComida();
        }).filter(i -> {
            //filtroEsBebida
            if (!c.esFiltraBebida()) return true;
            return i.getItemMenu().esBebida();
        }).filter(i -> {
            //filtroEsGaseosa
            if (!c.esFiltraGaseosa()) return true;
            if (!i.getItemMenu().esBebida()) return false;
            Bebida b = (Bebida) i.getItemMenu();
            return b.esGaseosa();
        }).filter(i -> {
            //filtroEsNoAlcoholica
            if (!c.esFiltraNoEsAlcoholico()) return true;
            // Si no es bebida , indica que es comida por lo tanto no es alcoholica
            if (!i.getItemMenu().esBebida()) return true;
            Bebida b = (Bebida) i.getItemMenu();
            return !b.esAlcoholica();
        }).filter(i -> {
            //filtroEsAptoVegano
            if (!c.esFiltraVegano()) return true;
            return i.getItemMenu().aptoVegano();
        }).filter(i -> {
            //filtroEsAptoCeliaco
            if (!c.esFiltraCeliaco()) return true;
            return i.getItemMenu().aptoCeliaco();
        }).filter(i -> {
            //filtraMenorPrecio
            if (!c.esFiltraPrecioMinimo()) return true;
            return i.getItemMenu().getPrecio() >= c.getPrecioMinimo();
        }).filter(i -> {
            //filtraMayorPrecio
            if (!c.esFiltraPrecioMaximo()) return true;
            return i.getItemMenu().getPrecio() <= c.getPrecioMaximo();
        }).filter(i -> {
            //filtraMayorPrecio
            if (!c.esFiltraRestaurante()) return true;
            return i.getItemMenu().getPrecio() <= c.getPrecioMaximo();
        }).toList();
        if (lista.isEmpty()) throw new ItemNoEncontradoException();
        return lista;
    }


    @Override
    public List<ItemPedido> buscarOrdenarPorNombre(CondicionesFiltradoItemPedido c, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemPedido> lista = this.filtrar(c);
        //Ordenar por nombre itemMenu
        int desc = descendente ? -1 : 1;
        // CompareTo devuelve negativo si es menor y positivo si es mayor, para invertirlo se multiplica por -1
        return lista.stream().sorted((i1, i2) -> i2.getItemMenu().getNombre().compareTo(i1.getItemMenu().getNombre()) * desc).toList();
    }

    @Override
    public List<ItemPedido> buscarOrdenarPorPrecio(CondicionesFiltradoItemPedido c, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemPedido> lista = this.filtrar(c);
        //Ordenar por precio itemMenu
        int desc = descendente ? -1 : 1;
        // Restamos y multiplicamos para definir si el orden es ascendente o descendente
        return lista.stream().sorted((i1, i2) -> (int) (i2.getItemMenu().getPrecio() - i1.getItemMenu().getPrecio()) * desc).toList();
    }
}
