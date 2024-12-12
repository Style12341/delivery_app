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
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Pedido createPedido(PedidoDTO pedidoDto) {
        Pedido pedido = new Pedido();
        Cliente c = clienteService.getClienteById(pedidoDto.getClienteId());
        Vendedor v = vendedorService.getVendedorById(pedidoDto.getVendedorId());
        if(v==null){
           throw new ResourceNotFoundException("Vendedor not found");
        }
        if(c==null){
            throw new ResourceNotFoundException("Cliente not found");
        }
        pedido.setCliente(c);
        pedido.setVendedor(v);
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido updatePedido(Long id, PedidoDTO pedidoDto) {
        Pedido p = pedidoRepository.findById(id).orElse(null);
        if (p == null) {
            throw new ResourceNotFoundException("Pedido not found");
        }
        if (!Objects.equals(p.getCliente().getId(), pedidoDto.getClienteId())) {
            Cliente c = clienteService.getClienteById(pedidoDto.getClienteId());
            if (c == null) {
                throw new ResourceNotFoundException("Cliente not found");
            }
            p.setCliente(c);
        }
        if (!Objects.equals(p.getVendedor().getId(), pedidoDto.getVendedorId())) {
            Vendedor v = vendedorService.getVendedorById(pedidoDto.getVendedorId());
            if (v == null) {
                throw new ResourceNotFoundException("Vendedor not found");
            }
            //Delete all items in the pedido if the vendedor changes
            p.setItems(new ArrayList<>());
            itemPedidoRepository.deleteAllByPedidoId(id);
            p.setVendedor(v);
        }
        p.setEstado(pedidoDto.getEstado());
        return pedidoRepository.save(p);
    }

    @Override
    @Transactional
    public Pedido addItemsToPedido(Long id, List<ItemPedidoDTO> items) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            throw new ResourceNotFoundException("Pedido" + id + " not found");
        }
        List<ItemPedido> itemsToSave = new ArrayList<>();
        for (ItemPedidoDTO item : items) {
            ItemMenu itemMenu = itemMenuService.findById(item.getItemMenuId());
            if (itemMenu == null) {
                throw new ResourceNotFoundException("ItemMenu" + item.getItemMenuId() + " not found");
            }
            ItemPedidoKey key = new ItemPedidoKey(itemMenu, pedido);
            ItemPedido itemPedido = pedido.findItemByItemMenuId(item.getItemMenuId());
            //Check if ItemMenu Belongs to the Pedido's vendedor
            if (!itemMenu.getVendedor().getId().equals(pedido.getVendedor().getId())) {
                throw new ResourceNotValidException("ItemMenu" + item.getItemMenuId() + " does not belong to the Pedido's vendedor");
            }
            if (itemPedido == null) {
                ItemPedido newItemPedido = new ItemPedido(key, item.getCantidad());
                itemsToSave.add(newItemPedido);
            } else {
                itemPedido.setCantidad(itemPedido.getCantidad() + item.getCantidad());
                itemsToSave.add(itemPedido);
            }
        }
        itemPedidoRepository.saveAll(itemsToSave);
        pedido.addItems(itemsToSave);
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido editItemsOfPedido(Long id, List<ItemPedidoDTO> items) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            throw new ResourceNotFoundException("Pedido" + id + " not found");
        }
        List<ItemPedido> itemsToSave = new ArrayList<>();
        for (ItemPedidoDTO item : items) {
            // Find the itemPedido in the database
            ItemMenu itemMenu = itemMenuService.findById(item.getItemMenuId());
            if (itemMenu == null) {
                throw new ResourceNotFoundException("ItemMenu" + item.getItemMenuId() + " not found");
            }
            ItemPedidoKey key = new ItemPedidoKey(itemMenu, pedido);
            ItemPedido itemPedido = pedido.findItemByItemMenuId(item.getItemMenuId());
            if (itemPedido != null) {
                Integer cantidad = itemPedido.getCantidad();
                if (cantidad == 0) {
                    itemPedidoRepository.delete(itemPedido);
                } else {
                    itemPedido.setCantidad(item.getCantidad());
                    itemsToSave.add(itemPedido);
                }
            }
        }
        itemPedidoRepository.saveAll(itemsToSave);
        // May cause problems if items sent in the request are not in the database
        // This is intentional, as the request should only contain items that are already in the database
        pedido.updateItems(itemsToSave);
        pedido.setItems(itemsToSave);
        return pedidoRepository.save(pedido);
    }

    @Override
    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
