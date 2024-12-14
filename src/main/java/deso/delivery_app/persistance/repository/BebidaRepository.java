package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.models.Comida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BebidaRepository extends JpaRepository<Bebida, Long>, JpaSpecificationExecutor<Bebida> {
    @Override
    @Query("SELECT c FROM Bebida c WHERE c.deleted_at IS NULL")
    List<Bebida> findAll();

    @Override
    @Query("SELECT c FROM Bebida c WHERE c.id = ?1 AND c.deleted_at IS NULL")
    Optional<Bebida> findById(Long id);
}
