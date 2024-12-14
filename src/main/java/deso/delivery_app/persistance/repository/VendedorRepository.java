package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.Cliente;
import deso.delivery_app.persistance.models.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    @Override
    @Query("SELECT c FROM Vendedor c WHERE c.deleted_at IS NULL")
    List<Vendedor> findAll();

    @Override
    @Query("SELECT c FROM Vendedor c WHERE c.id = ?1 AND c.deleted_at IS NULL")
    Optional<Vendedor> findById(Long id);
}