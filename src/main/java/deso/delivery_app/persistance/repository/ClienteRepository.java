package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Override
    @Query("SELECT c FROM Cliente c WHERE c.deleted_at IS NULL")
    List<Cliente> findAll();

    @Override
    @Query("SELECT c FROM Cliente c WHERE c.id = ?1 AND c.deleted_at IS NULL")
    Optional<Cliente> findById(Long id);
}
