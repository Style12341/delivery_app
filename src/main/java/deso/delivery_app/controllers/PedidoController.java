// src/main/java/deso/delivery_app/controllers/PedidoController.java
package deso.delivery_app.controllers;

import deso.delivery_app.dto.PedidoDTO;
import deso.delivery_app.dto.PedidoFilterDTO;
import deso.delivery_app.enums.ESTADO_PEDIDO;
import deso.delivery_app.persistance.models.Pedido;
import deso.delivery_app.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        Pedido pedido = pedidoService.getPedidoById(id);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody PedidoDTO pedido) {

        Pedido createdPedido = pedidoService.createPedido(pedido);
        return ResponseEntity.ok(createdPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody PedidoDTO pedido) {
        Pedido updatedPedido = pedidoService.updatePedido(id, pedido);
        return updatedPedido != null ? ResponseEntity.ok(updatedPedido) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Pedido>> getMatchingPedidos(@RequestParam(required = false) Long id,
                                                           @RequestParam(required = false) Long clienteId,
                                                           @RequestParam(required = false) Long vendedorId,
                                                           @RequestParam(required = false) Double minPrice,
                                                           @RequestParam(required = false) Double maxPrice,
                                                           @RequestParam(required = false) ESTADO_PEDIDO estado) {
        PedidoFilterDTO filter = new PedidoFilterDTO(id, estado, minPrice, maxPrice, clienteId, vendedorId);

        List<Pedido> pedidos = pedidoService.getMatchingPedidos(filter);
        return ResponseEntity.ok(pedidos);
    }
}