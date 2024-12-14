package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.Comida;
import deso.delivery_app.persistance.models.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComidaRepository extends JpaRepository<Comida, Long>, JpaSpecificationExecutor<Comida> {
    @Override
    @Query("SELECT c FROM Comida c WHERE c.deleted_at IS NULL")
    List<Comida> findAll();

    @Override
    @Query("SELECT c FROM Comida c WHERE c.id = ?1 AND c.deleted_at IS NULL")
    Optional<Comida> findById(Long id);
}