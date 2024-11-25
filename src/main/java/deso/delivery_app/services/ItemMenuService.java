package deso.delivery_app.services;

import deso.delivery_app.dto.BebidaFilterDTO;
import deso.delivery_app.dto.ComidaFilterDTO;
import deso.delivery_app.dto.MenuDTO;
import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.Comida;
import deso.delivery_app.persistance.models.ItemMenu;

public interface ItemMenuService {
    BebidaService bebidaService = null;
    ComidaService comidaService = null;

    public void delete(Long id);

    public ItemMenu findById(Long id);

    public MenuDTO getAllItemMenus();

    public MenuDTO getMatchingItemMenus(ItemMenuFilterDTO itemMenuFilter);

    public Comida createItem(Comida itemMenu);

    public Bebida createItem(Bebida itemMenu);

    public Comida updateItem(Long id, Comida itemMenu);

    public Bebida updateItem(Long id, Bebida itemMenu);
}
