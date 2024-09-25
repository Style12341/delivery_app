package deso.delivery_app.persistence.DAO;

import deso.delivery_app.Coordenada;
import deso.delivery_app.ItemPedido;
import deso.delivery_app.Vendedor;
import deso.delivery_app.exception.ItemNoEncontradoException;

import java.util.ArrayList;
import java.util.List;

public class VendedorMemory implements VendedorDao {
    ArrayList<Vendedor> vendedorMemory = new ArrayList<>();
    @Override
    public Vendedor create(Vendedor vendedor) {
        vendedorMemory.add(vendedor);
        return vendedor;
    }

    @Override
    public Vendedor get(long id)
    {
            return vendedorMemory.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Vendedor update(Vendedor vendedor) {
            for (int i = 0; i < vendedorMemory.size(); i++) {
                if(vendedorMemory.get(i).getId() == vendedor.getId()) {
                    vendedorMemory.set(i, vendedor);
                    return vendedor;
                }
            }
            return null;
    }

    @Override
    public void delete(long id) {
        vendedorMemory.removeIf(v -> v.getId() == id);
    }

    @Override
    public List<Vendedor> filtrar(FiltrosVendedor f) throws ItemNoEncontradoException {
        // Si es comida o bebida ->
        List<Vendedor> lista = vendedorMemory.stream().filter(f.getFiltros()).toList();
        if (lista.isEmpty()) throw new ItemNoEncontradoException("Item no encontrado");
        return lista;
    }

    @Override
    public List<Vendedor> buscarOrdenarPorNombre(FiltrosVendedor f, Boolean descendente) throws ItemNoEncontradoException {
        List<Vendedor> lista = this.filtrar(f);
        int desc = descendente ? 1 : -1;
        // CompareTo devuelve negativo si es menor y positivo si es mayor, para invertirlo se multiplica por -1
        return lista.stream().sorted((v1, v2) -> v2.getNombre().compareTo(v1.getNombre()) * desc).toList();
    }

    @Override
    public List<Vendedor> buscarOrdenarPorProximidad(FiltrosVendedor f, Coordenada coord, Boolean descendente) throws ItemNoEncontradoException {
        List<Vendedor> lista = this.filtrar(f);
        // Se ordena por distancia entre coord y coordenada de Vendedor
        int desc = descendente ? 1 : -1;
        return lista.stream().sorted((v1, v2) -> (int) (Double.compare(coord.calcularDistancia(v1.getCoordenadas()),coord.calcularDistancia(v2.getCoordenadas()))) * desc).toList();
    }
}
