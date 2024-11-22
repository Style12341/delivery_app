package deso.delivery_app.persistance.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Entity
@Table(name = "cliente")
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
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    public Coordenada setCoodenada(Coordenada coordenada) {
        this.coordenada = coordenada;
        return coordenada;
    }
}
