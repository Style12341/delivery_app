package deso.delivery_app.controllers;

import deso.delivery_app.persistance.models.Cliente;
import deso.delivery_app.services.ClienteService;
import deso.delivery_app.services.impl.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Cliente>> getMatchingClientes(@RequestParam(required = false) Long id,
                                                             @RequestParam(required = false) String nombre,
                                                             @RequestParam(required = false) String apellido,
                                                             @RequestParam(required = false) String direccion,
                                                             @RequestParam(required = false) String cuit,
                                                             @RequestParam(required = false) String email) {

        Cliente clienteFilter = new Cliente();
        clienteFilter.setId(id);
        clienteFilter.setApellido(apellido);
        clienteFilter.setEmail(email);
        clienteFilter.setNombre(nombre);
        clienteFilter.setDireccion(direccion);
        clienteFilter.setCuit(cuit);

        List<Cliente> clientes = clienteService.getMatchingClientes(clienteFilter);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return cliente == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente newCliente = clienteService.createCliente(cliente);
        return ResponseEntity.ok(newCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente updatedCliente = clienteService.updateCliente(id, cliente);
        return updatedCliente == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}