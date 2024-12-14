package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.ItemMenu;
import deso.delivery_app.persistance.models.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemMenuRepository extends JpaRepository<ItemMenu, Long> {
    @Override
    @Query("SELECT c FROM ItemMenu c WHERE c.deleted_at IS NULL")
    List<ItemMenu> findAll();

    @Override
    @Query("SELECT c FROM ItemMenu c WHERE c.id = ?1 AND c.deleted_at IS NULL")
    Optional<ItemMenu> findById(Long id);
}
