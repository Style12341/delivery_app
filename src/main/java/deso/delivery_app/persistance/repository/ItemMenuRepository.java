package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemMenuRepository extends JpaRepository<ItemMenu, Long> {
}
