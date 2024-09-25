package deso.delivery_app.persistence.DAO;

import deso.delivery_app.ItemMenu;
import deso.delivery_app.exception.ItemNoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemsMenuMemory implements ItemsMenuDAO{
    private static ItemsMenuMemory SINGLETON_INSTANCE;
    private final List<ItemMenu> listaItemsMenu;

    private ItemsMenuMemory() {
        listaItemsMenu = new ArrayList<>();
    }

    public static ItemsMenuMemory getInstance() {
        if (SINGLETON_INSTANCE == null) SINGLETON_INSTANCE = new ItemsMenuMemory();
        return SINGLETON_INSTANCE;
    }

    @Override
    public ItemMenu create(ItemMenu ItemMenu) {
        listaItemsMenu.add(ItemMenu);
        return ItemMenu;
    }

    @Override
    public ItemMenu get(long id) {
        return listaItemsMenu.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    public ItemMenu update(ItemMenu ItemMenu) {
        for (int i = 0; i < listaItemsMenu.size(); i++) {
            if(listaItemsMenu.get(i).getId() == ItemMenu.getId()) {
                listaItemsMenu.set(i, ItemMenu);
                return ItemMenu;
            }
        }
        return null;
    }

    @Override
    public void delete(long id) {
        Optional<ItemMenu> item = listaItemsMenu.stream().filter(i -> i.getId() == id).findFirst();
        item.ifPresent(listaItemsMenu::remove);
    }

    @Override
    public List<ItemMenu> filtrar(FiltrosItemMenu filters) throws ItemNoEncontradoException {
        // Si es comida o bebida ->
        List<ItemMenu> lista = listaItemsMenu.stream().filter(filters.getFiltros()).toList();
        if (lista.isEmpty()) throw new ItemNoEncontradoException("Item no encontrado");
        return lista;
    }


    @Override
    public List<ItemMenu> buscarOrdenarPorNombre(FiltrosItemMenu filters, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemMenu> lista = this.filtrar(filters);
        //Ordenar por nombre itemMenu
        int desc = descendente ? 1 : -1;
        // CompareTo devuelve negativo si es menor y positivo si es mayor, para invertirlo se multiplica por -1
        return lista.stream().sorted((i1, i2) -> i2.getNombre().compareTo(i1.getNombre()) * desc).toList();
    }

    @Override
    public List<ItemMenu> buscarOrdenarPorPrecio(FiltrosItemMenu filters, Boolean descendente) throws ItemNoEncontradoException {
        List<ItemMenu> lista = this.filtrar(filters);
        //Ordenar por precio itemMenu
        int desc = descendente ? 1 : -1;
        // Restamos y multiplicamos para definir si el orden es ascendente o descendente
        return lista.stream()
                .sorted((i1, i2) -> Double.compare(i2.getPrecio(), i1.getPrecio()) * desc)
                .toList();
    }
}
