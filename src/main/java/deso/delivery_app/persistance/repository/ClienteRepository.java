package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
