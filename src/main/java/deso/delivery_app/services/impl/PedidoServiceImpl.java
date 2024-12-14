package deso.delivery_app.services.impl;

import deso.delivery_app.dto.ItemPedidoDTO;
import deso.delivery_app.dto.PedidoDTO;
import deso.delivery_app.dto.PedidoFilterDTO;
import deso.delivery_app.exceptions.ResourceNotFoundException;
import deso.delivery_app.exceptions.ResourceNotValidException;
import deso.delivery_app.persistance.models.*;
import deso.delivery_app.persistance.models.composed_keys.ItemPedidoKey;
import deso.delivery_app.persistance.repository.ItemPedidoRepository;
import deso.delivery_app.persistance.repository.PedidoRepository;
import deso.delivery_app.services.ClienteService;
import deso.delivery_app.services.ItemMenuService;
import deso.delivery_app.services.PedidoService;
import deso.delivery_app.services.VendedorService;
import deso.delivery_app.specifications.PedidoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ItemMenuService itemMenuService;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public List<Pedido> getMatchingPedidos(PedidoFilterDTO pedidoFilter) {
        Specification<Pedido> spec = PedidoSpecification.fromFilter(pedidoFilter);
        return pedidoRepository.findAll(spec);
    }

    @Override
    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public Pedido createPedido(PedidoDTO pedidoDto) {
        Pedido pedido = new Pedido();
        Cliente c = clienteService.getClienteById(pedidoDto.getClienteId());
        Vendedor v = vendedorService.getVendedorById(pedidoDto.getVendedorId());
        if(v==null){
           throw new ResourceNotValidException("Vendedor not found");
        }
        if(c==null){
            throw new ResourceNotValidException("Cliente not found");
        }
        pedido.setCliente(c);
        pedido.setVendedor(v);
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido updatePedido(Long id, PedidoDTO pedidoDto) {
        Pedido p = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido with ID " + id + " not found"));
        if (!Objects.equals(p.getCliente().getId(), pedidoDto.getClienteId())) {
            Cliente c = clienteService.getClienteById(pedidoDto.getClienteId());
            if (c == null) {
                throw new ResourceNotValidException("Cliente not found");
            }
            p.setCliente(c);
        }
        if (!Objects.equals(p.getVendedor().getId(), pedidoDto.getVendedorId())) {
            Vendedor v = vendedorService.getVendedorById(pedidoDto.getVendedorId());
            if (v == null) {
                throw new ResourceNotValidException("Vendedor not found");
            }
            //Delete all items in the pedido if the vendedor changes
            p.setItems(new ArrayList<>());
            p.setVendedor(v);
        }
        p.setEstado(pedidoDto.getEstado());
        return pedidoRepository.save(p);
    }

    @Override
    @Transactional
    public Pedido addItemsToPedido(Long id, List<ItemPedidoDTO> items) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido with ID " + id + " not found"));

        List<ItemPedido> updatedItems = new ArrayList<>(pedido.getItems());

        for (ItemPedidoDTO item : items) {
            ItemMenu itemMenu = itemMenuService.findById(item.getItemMenuId());
            if (itemMenu == null) {
                throw new ResourceNotValidException("ItemMenu " + item.getItemMenuId() + " not found");
            }

            // Check if ItemMenu Belongs to the Pedido's vendedor
            if (!itemMenu.getVendedor().getId().equals(pedido.getVendedor().getId())) {
                throw new ResourceNotValidException("ItemMenu " + item.getItemMenuId() + " does not belong to the Pedido's vendedor");
            }

            // Find existing item in the pedido
            ItemPedido existingItemPedido = pedido.findItemByItemMenuId(item.getItemMenuId());

            if (existingItemPedido != null) {
                // Update quantity of existing item
                existingItemPedido.setCantidad(existingItemPedido.getCantidad() + item.getCantidad());
                updatedItems.add(existingItemPedido);
            } else {
                // Create new item
                ItemPedidoKey key = new ItemPedidoKey(itemMenu, pedido);
                ItemPedido newItemPedido = new ItemPedido(key, item.getCantidad());
                updatedItems.add(newItemPedido);
            }
        }

        // Clear existing items and add updated items
        pedido.getItems().clear();
        pedido.getItems().addAll(updatedItems);

        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido editItemsOfPedido(Long id, List<ItemPedidoDTO> items) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido with ID " + id + " not found"));

        // Create a new list to store updated items
        List<ItemPedido> updatedItems = new ArrayList<>();

        for (ItemPedidoDTO item : items) {
            ItemMenu itemMenu = itemMenuService.findById(item.getItemMenuId());
            if (itemMenu == null) {
                throw new ResourceNotFoundException("ItemMenu " + item.getItemMenuId() + " not found");
            }

            ItemPedido existingItemPedido = pedido.findItemByItemMenuId(item.getItemMenuId());

            if (item.getCantidad() == 0 && existingItemPedido != null) {
                // If cantidad is 0, remove the item
                pedido.getItems().remove(existingItemPedido);
                itemPedidoRepository.delete(existingItemPedido);
            } else if (existingItemPedido != null) {
                // Update existing item
                existingItemPedido.setCantidad(item.getCantidad());
                updatedItems.add(existingItemPedido);
            } else if (item.getCantidad() > 0) {
                // Create new item if cantidad > 0
                ItemPedidoKey key = new ItemPedidoKey(itemMenu, pedido);
                ItemPedido newItemPedido = new ItemPedido(key, item.getCantidad());
                updatedItems.add(newItemPedido);
            }
        }

        // Clear existing items and add updated items
        pedido.getItems().clear();
        pedido.getItems().addAll(updatedItems);

        return pedidoRepository.save(pedido);
    }

    @Override
    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
