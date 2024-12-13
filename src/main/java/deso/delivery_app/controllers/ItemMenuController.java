package deso.delivery_app.controllers;

import deso.delivery_app.dto.BebidaFilterDTO;
import deso.delivery_app.dto.ComidaFilterDTO;
import deso.delivery_app.dto.MenuDTO;
import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.Comida;
import deso.delivery_app.services.ItemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item-menu")
public class ItemMenuController {
    private static final String MAX_DOUBLE = "999999999999";
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
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    @PutMapping("/comida/{id}")
    public ResponseEntity<Comida> updateComida(@PathVariable Long id, @RequestBody Comida comida) {
        Comida updatedComida = itemMenuService.updateItem(id, comida);
        return ResponseEntity.ok(updatedComida);
    }

    @PostMapping("/bebida")
    public ResponseEntity<Bebida> createBebida(@RequestBody Bebida bebida) {
        Bebida b = itemMenuService.createItem(bebida);
        return ResponseEntity.ok(b);
    }

    @PutMapping("/bebida/{id}")
    public ResponseEntity<Bebida> updateBebida(@PathVariable Long id, @RequestBody Bebida bebida) {
        Bebida updatedBebida = itemMenuService.updateItem(id, bebida);
        return ResponseEntity.ok(updatedBebida);
    }

    @GetMapping("/search")
    public ResponseEntity<MenuDTO> getMatchingItemMenus(
            @RequestParam(required = false) Long vendedorId,
            @RequestParam(required = false, defaultValue = "0.0") Double precioMin,
            @RequestParam(required = false, defaultValue = MAX_DOUBLE) Double precioMax,
            @RequestParam(required = false) Boolean esAptoCeliaco,
            @RequestParam(required = false) Boolean esAptoVegano,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false, defaultValue = "false") Boolean soloComidas,
            @RequestParam(required = false, defaultValue = "false") Boolean soloBebidas,
            @RequestParam(required = false, defaultValue = "0.0") Double pesoMin,
            @RequestParam(required = false, defaultValue = MAX_DOUBLE) Double pesoMax,
            @RequestParam(required = false) Boolean esAlcoholica,
            @RequestParam(required = false) Boolean esGaseosa,
            @RequestParam(required = false, defaultValue = "0.0") Double volumenMin,
            @RequestParam(required = false, defaultValue = MAX_DOUBLE) Double volumenMax,
            @RequestParam(required = false, defaultValue = "0.0") Double graduacionAlcoholicaMin,
            @RequestParam(required = false, defaultValue = "100.0") Double graduacionAlcoholicaMax) {
        BebidaFilterDTO bebidaFilter = new BebidaFilterDTO(
                esAlcoholica, esGaseosa, volumenMin, volumenMax, graduacionAlcoholicaMin, graduacionAlcoholicaMax);
        ComidaFilterDTO comidaFilter = new ComidaFilterDTO();
        ItemMenuFilterDTO filter = new ItemMenuFilterDTO(
                vendedorId,
                precioMin,
                precioMax,
                esAptoCeliaco,
                esAptoVegano,
                nombre,
                soloComidas,
                soloBebidas,
                pesoMin,
                pesoMax,
                comidaFilter,
                bebidaFilter);

        MenuDTO menu = itemMenuService.getMatchingItemMenus(filter);
        return ResponseEntity.ok(menu);
    }
}
