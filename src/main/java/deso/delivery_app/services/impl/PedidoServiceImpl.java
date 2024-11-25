package deso.delivery_app.services.impl;

import deso.delivery_app.dto.PedidoDTO;
import deso.delivery_app.dto.PedidoFilterDTO;
import deso.delivery_app.persistance.models.Cliente;
import deso.delivery_app.persistance.models.Pedido;
import deso.delivery_app.persistance.models.Vendedor;
import deso.delivery_app.persistance.repository.PedidoRepository;
import deso.delivery_app.services.ClienteService;
import deso.delivery_app.services.PedidoService;
import deso.delivery_app.services.VendedorService;
import deso.delivery_app.specifications.PedidoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public Pedido createPedido(PedidoDTO pedidoDto) {
        Pedido pedido = new Pedido();
        Cliente c = clienteService.getClienteById(pedidoDto.getClienteId());
        Vendedor v = vendedorService.getVendedorById(pedidoDto.getVendedorId());
        if (v == null || c == null) {
            return null; // excepcion a
        }
        pedido.setCliente(c);
        pedido.setVendedor(v);
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido updatePedido(Long id, PedidoDTO pedidoDto) {
        Pedido p = pedidoRepository.findById(id).orElse(null);
        if (p != null) {
            if (!Objects.equals(p.getCliente().getId(), pedidoDto.getClienteId())) {
                Cliente c = clienteService.getClienteById(pedidoDto.getClienteId());
                if (c == null) {
                    return null;
                }
                p.setCliente(c);
            }
            if (!Objects.equals(p.getVendedor().getId(), pedidoDto.getVendedorId())) {
                Vendedor v = vendedorService.getVendedorById(pedidoDto.getVendedorId());
                if (v == null) {
                    return null;
                }
                p.setVendedor(v);
            }
            p.setEstado(pedidoDto.getEstado());
            return pedidoRepository.save(p);
        } else {
            // Handle the case where the Pedido does not exist
            return null; // Or throw an exception
        }
    }

    @Override
    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
