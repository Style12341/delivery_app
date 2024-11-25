package deso.delivery_app.persistance.models;

import deso.delivery_app.enums.TIPO_ITEM;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "item_menu")
@SQLDelete(sql = "UPDATE item_menu SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
public abstract class ItemMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Double precio;
    @Column(nullable = false)
    private TIPO_ITEM categoria;
    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;
    @Column(name = "apto_celiaco", nullable = false)
    private Boolean esAptoCeliaco;
    @Column(name = "apto_vegano", nullable = false)
    private Boolean esAptoVegano;
    @Column
    private LocalDateTime deleted_at;
    private Double peso;

}
