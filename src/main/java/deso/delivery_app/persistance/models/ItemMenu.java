package deso.delivery_app.persistance.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import deso.delivery_app.enums.TIPO_ITEM;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "item_menu")
@SQLDelete(sql = "UPDATE item_menu SET deleted_at = now() WHERE id = ?")
//@Where(clause = "deleted_at is null")
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
    @OneToMany(mappedBy = "id.itemMenu")
    @JsonIgnore
    private List<ItemPedido> items;
    @Column(name = "apto_celiaco", nullable = false)
    private Boolean esAptoCeliaco;
    @Column(name = "apto_vegano", nullable = false)
    private Boolean esAptoVegano;
    @Column
    @JsonIgnore
    private LocalDateTime deleted_at;
    @Column
    protected Double peso;

}
