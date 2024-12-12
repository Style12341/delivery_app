package deso.delivery_app.controllers;

import deso.delivery_app.persistance.models.Coordenada;
import deso.delivery_app.persistance.models.Vendedor;
import deso.delivery_app.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {
    @Autowired
    private VendedorService vendedorService;

    @Autowired
    public VendedorController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }


    @GetMapping
    public List<Vendedor> getAllVendedores() {
        return vendedorService.getAllVendedores();
    }

    @GetMapping("/search")
    public List<Vendedor> getMatchingVendedores(@RequestParam(required = false) Long id,
                                                @RequestParam(required = false) String nombre,
                                                @RequestParam(required = false) String direccion,
                                                @RequestParam(required = false) String cuit,
                                                @RequestParam(required = false) Coordenada coordenada
                                               ) {

        Vendedor vendedorFilter = new Vendedor();
        vendedorFilter.setId(id);
        vendedorFilter.setNombre(nombre);
        vendedorFilter.setDireccion(direccion);
        vendedorFilter.setCuit(cuit);
        vendedorFilter.setCoordenada(coordenada);
        return vendedorService.getMatchingVendedores(vendedorFilter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> getVendedorById(@PathVariable Long id) {
        Vendedor vendedor = vendedorService.getVendedorById(id);
        return ResponseEntity.ok(vendedor);
    }

    @PostMapping
    public ResponseEntity<Vendedor> createVendedor(@RequestBody Vendedor vendedor) {
        Vendedor createdVendedor = vendedorService.createVendedor(vendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVendedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> updateVendedor(@PathVariable Long id, @RequestBody Vendedor vendedor) {
        Vendedor updatedVendedor = vendedorService.updateVendedor(id, vendedor);
        return ResponseEntity.ok(updatedVendedor);
    }

    //soft delete, return 200 OK
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable Long id) {
        vendedorService.deleteVendedor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<Void> getVendedorMenu(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }


}
