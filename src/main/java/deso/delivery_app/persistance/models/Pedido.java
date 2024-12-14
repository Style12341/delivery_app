package deso.delivery_app.persistance.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import deso.delivery_app.enums.ESTADO_PEDIDO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pedido")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Transient
    private Double precioAcumulado = 0.0;

    @Column(nullable = false)
    private ESTADO_PEDIDO estado = ESTADO_PEDIDO.RECIBIDO;

    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> items;

    @PostLoad
    public void calculatePrecioAcumulado() {
        this.precioAcumulado = items.stream()
                .mapToDouble(ItemPedido::getPrecioTotal)
                .sum();
    }

    public void addItems(List<ItemPedido> items) {
        this.items.addAll(items);
    }
    public ItemPedido findItemByItemMenuId(Long itemMenuId) {
        return items.stream()
                .filter(itemPedido -> itemPedido.getItemMenuId().equals(itemMenuId))
                .findFirst()
                .orElse(null);
    }
}
