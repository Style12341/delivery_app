package deso.delivery_app.persistance.repository;

import deso.delivery_app.persistance.models.Bebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BebidaRepository extends JpaRepository<Bebida, Long>, JpaSpecificationExecutor<Bebida> {
}
