package deso.delivery_app.services;

import deso.delivery_app.dto.ItemPedidoDTO;
import deso.delivery_app.dto.PedidoDTO;
import deso.delivery_app.dto.PedidoFilterDTO;
import deso.delivery_app.persistance.models.ItemPedido;
import deso.delivery_app.persistance.models.Pedido;
import deso.delivery_app.persistance.models.Pedido;

import java.util.List;

public interface PedidoService {
    public List<Pedido> getAllPedidos();

    public List<Pedido> getMatchingPedidos(PedidoFilterDTO pedidoFilter);

    public Pedido getPedidoById(Long id);

    public Pedido createPedido(PedidoDTO pedido);

    public Pedido updatePedido(Long id, PedidoDTO pedido);

    public Pedido addItemsToPedido(Long id, List<ItemPedidoDTO> items);

    public Pedido editItemsOfPedido(Long id, List<ItemPedidoDTO> items);

    public void deletePedido(Long id);
}
