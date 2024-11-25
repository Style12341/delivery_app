package deso.delivery_app.persistance.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "vendedor")
@SQLDelete(sql = "UPDATE vendedor SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Embedded
    private Coordenada coordenada;
    @Column(nullable = false,unique = true)
    private String cuit;
    @Column(nullable = false)
    private String direccion;
    @JsonIgnore
    @OneToMany(mappedBy = "vendedor")
    private List<Pedido> pedidos;
    @JsonIgnore
    @OneToMany(mappedBy = "vendedor")
    private List<ItemMenu> items;
    @Column
    @JsonIgnore
    private LocalDateTime deleted_at;

}
