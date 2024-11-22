package deso.delivery_app.persistance.models;

import deso.delivery_app.enums.ESTADO_PEDIDO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido")
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
    private Double precio_acumulado;

    @Column(nullable = false)
    private ESTADO_PEDIDO estado;

}
