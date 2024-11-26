package deso.delivery_app.persistance.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import deso.delivery_app.enums.ESTADO_PEDIDO;
import deso.delivery_app.persistance.models.listeners.ItemPedidoListener;
import deso.delivery_app.persistance.models.listeners.PedidoListener;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "pedido")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(PedidoListener.class)
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

    @Column(nullable = false)
    private Double precioAcumulado = 0.0;

    @Column(nullable = false)
    private ESTADO_PEDIDO estado = ESTADO_PEDIDO.RECIBIDO;

    @OneToMany(mappedBy = "id.pedido")
    private List<ItemPedido> items;

}
