package deso.delivery_app.persistance.repository;


import deso.delivery_app.persistance.models.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMenuRepository<T extends ItemMenu> extends JpaRepository<T, Long> {
}
