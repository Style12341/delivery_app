package deso.delivery_app.persistance.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "cliente")
@SQLDelete(sql = "UPDATE cliente SET deleted_at = now() WHERE id = ?")
//@Where(clause = "deleted_at is null")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(unique = true, nullable = false)
    private String cuit;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String direccion;
    @Getter
    @Embedded
    private Coordenada coordenada;
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
    @Column
    @JsonIgnore
    private LocalDateTime deleted_at;
}
