package deso.delivery_app.controllers;

import deso.delivery_app.dto.MenuDTO;
import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.Comida;
import deso.delivery_app.services.ItemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item-menu")
public class ItemMenuController {
    // Nombre, rango precio, comida vegana, comida celiaca, no alcoholica, gaseosa, bebida, gaseosa, comida, vendedor
    @Autowired
    private ItemMenuService itemMenuService;

    @GetMapping
    public ResponseEntity<MenuDTO> getAllItemMenus() {
        MenuDTO itemMenuDTO = itemMenuService.getAllItemMenus();
        return ResponseEntity.ok(itemMenuDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemMenu(@PathVariable Long id) {
        itemMenuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comida")
    public ResponseEntity<Comida> createComida(@RequestBody Comida comida) {
        Comida c = itemMenuService.createItem(comida);
        return ResponseEntity.ok(c);
    }

    @PutMapping("/comida/{id}")
    public ResponseEntity<Comida> updateComida(@PathVariable Long id, @RequestBody Comida comida) {
        Comida updatedComida = itemMenuService.updateItem(id, comida);
        return updatedComida != null ? ResponseEntity.ok(updatedComida) : ResponseEntity.notFound().build();
    }

    @PostMapping("/bebida")
    public ResponseEntity<Bebida> createBebida(@RequestBody Bebida bebida) {
        Bebida b = itemMenuService.createItem(bebida);
        return ResponseEntity.ok(b);
    }

    @PutMapping("/bebida/{id}")
    public ResponseEntity<Bebida> updateBebida(@PathVariable Long id, @RequestBody Bebida bebida) {
        Bebida updatedBebida = itemMenuService.updateItem(id, bebida);
        return updatedBebida != null ? ResponseEntity.ok(updatedBebida) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<MenuDTO> getMatchingItemMenus(@RequestBody ItemMenuFilterDTO filter) {
        MenuDTO menu = itemMenuService.getMatchingItemMenus(filter);
        return ResponseEntity.ok(menu);
    }
}
