package deso.delivery_app.persistance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Coordenada {
    @Column(nullable = false)
    private Double latitud;
    @Column(nullable = false)
    private Double longitud;
}
