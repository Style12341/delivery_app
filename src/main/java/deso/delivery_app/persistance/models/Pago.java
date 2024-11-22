package deso.delivery_app.persistance.models;

import deso.delivery_app.enums.METODO_PAGO;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime fecha;
    @Column (name = "precio_total_sin_recargo",nullable = false)
    private Double precioTotalSinRecargo;
    @Column(name = "precio_total_con_recargo",nullable = false )
    private Double precioTotalConRecargo;
    @Column(nullable = false)
    private METODO_PAGO metodo;
}
