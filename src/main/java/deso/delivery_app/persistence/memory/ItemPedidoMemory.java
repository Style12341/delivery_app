package deso.delivery_app.persistence.memory;

import deso.delivery_app.models.ItemPedido;
import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.persistence.filters.FiltrosItemPedido;
import deso.delivery_app.persistence.DAO.ItemPedidoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//singleton

public class ItemPedidoMemory implements ItemPedidoDAO {

    private static ItemPedidoMemory SINGLETON_INSTANCE;
    private final List<ItemPedido> listaItemsPedido;

    private ItemPedidoMemory() {
        listaItemsPedido = new ArrayList<>();
    }

    public static ItemPedidoMemory getInstance() {
        if (SINGLETON_INSTANCE == null) SINGLETON_INSTANCE = new ItemPedidoMemory();
        return SINGLETON_INSTANCE;
    }

    @Override
    public ItemPedido create(ItemPedido itemPedido) {
        listaItemsPedido.add(itemPedido);
        return itemPedido;
    }

    @Override
    public ItemPedido get(long idPedido, long idItemMenu) {
        return listaItemsPedido.stream().filter(i -> i.getPedido().getId() == idPedido && i.getItemMenu().getId() == idItemMenu).findFirst().orElse(null);
    }

    public ItemPedido update(ItemPedido itemPedido) {
        for (int i = 0; i < listaItemsPedido.size(); i++) {
            if (listaItemsPedido.get(i).equals(itemPedido)) {
                listaItemsPedido.set(i, itemPedido);
                return itemPedido;
            }
            return null;
        }
        return null;
    }

    @Override
    public void delete(long idPedido, long idItemMenu) {
        Optional<ItemPedido> item = listaItemsPedido.stream().filter(i -> i.getPedido().getId() == idPedido && i.getItemMenu().getId() == idItemMenu).findFirst();
        item.ifPresent(listaItemsPedido::remove);
    }

    @Override
    public List<ItemPedido> filtrar(FiltrosItemPedido filters) throws ItemNoEncontradoException {
        // Si es comida o bebida ->
        List<ItemPedido> lista = listaItemsPedido.stream().filter(filters.getFiltros()).toList();
        if (lista.isEmpty()) throw new ItemNoEncontradoException("Item no encontrado");
        return lista;
    }


    @Override
    public List<ItemPedido> buscarOrdenarPorNombre(FiltrosItemPedido filters, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemPedido> lista = this.filtrar(filters);
        //Ordenar por nombre itemMenu
        int desc = descendente ? 1 : -1;
        // CompareTo devuelve negativo si es menor y positivo si es mayor, para invertirlo se multiplica por -1
        return lista.stream().sorted((i1, i2) -> i2.getItemMenu().getNombre().compareTo(i1.getItemMenu().getNombre()) * desc).toList();
    }

    @Override
    public List<ItemPedido> buscarOrdenarPorPrecio(FiltrosItemPedido filters, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemPedido> lista = this.filtrar(filters);
        //Ordenar por precio itemMenu
        int desc = descendente ? 1 : -1;
        // Restamos y multiplicamos para definir si el orden es ascendente o descendente
        return lista.stream()
                .sorted((i1, i2) -> Double.compare(i2.getPrecioTotal(), i1.getPrecioTotal()) * desc)
                .toList();
    }
}
