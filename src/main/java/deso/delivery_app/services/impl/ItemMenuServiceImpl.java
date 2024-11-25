// ItemMenuServiceImpl.java
package deso.delivery_app.services.impl;

import deso.delivery_app.dto.BebidaFilterDTO;
import deso.delivery_app.dto.ComidaFilterDTO;
import deso.delivery_app.dto.MenuDTO;
import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.Comida;
import deso.delivery_app.persistance.models.ItemMenu;
import deso.delivery_app.persistance.repository.ItemMenuRepository;
import deso.delivery_app.services.BebidaService;
import deso.delivery_app.services.ComidaService;
import deso.delivery_app.services.ItemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemMenuServiceImpl implements ItemMenuService {

    @Autowired
    private ItemMenuRepository itemMenuRepository;
    @Autowired
    private BebidaService bebidaService;
    @Autowired
    private ComidaService comidaService;

    @Override
    public void delete(Long id) {
        itemMenuRepository.deleteById(id);
    }

    @Override
    public ItemMenu findById(Long id) {
        return itemMenuRepository.findById(id).orElse(null);
    }

    @Override
    public MenuDTO getAllItemMenus() {
        List<Comida> comidas = comidaService.getAllComidas();
        List<Bebida> bebidas = bebidaService.getAllBebidas();
        return new MenuDTO(comidas, bebidas);
    }

    @Override
    public Comida createItem(Comida itemMenu) {
        return comidaService.createComida(itemMenu);
    }

    @Override
    public Bebida createItem(Bebida itemMenu) {
        return bebidaService.createBebida(itemMenu);
    }

    @Override
    public Comida updateItem(Long id, Comida itemMenu) {
        return comidaService.updateComida(id, itemMenu);
    }

    @Override
    public Bebida updateItem(Long id, Bebida itemMenu) {
        return bebidaService.updateBebida(id, itemMenu);
    }

    @Override
    public MenuDTO getMatchingItemMenus(ItemMenuFilterDTO itemMenuFilter) {
        List<Comida> comidas = comidaService.getMatchingComidas(itemMenuFilter);
        List<Bebida> bebidas = bebidaService.getMatchingBebidas(itemMenuFilter);
        return new MenuDTO(comidas, bebidas);
    }
}