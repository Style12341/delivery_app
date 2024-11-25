package deso.delivery_app.services;

import deso.delivery_app.persistance.models.ItemMenu;

import java.util.List;

public interface ItemMenuService<T extends ItemMenu> {
    public T create(T itemMenu);

    public T update(T itemMenu);

    public void delete(Long id);

    public T findById(Long id);

    public List<T> findAll();

    public List<ItemMenu> getMatchingItemMenus(ItemMenu itemMenuFilter);



}
