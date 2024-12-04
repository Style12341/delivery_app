package deso.delivery_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class BebidaFilterDTO {
    Boolean alcoholica;
    Boolean gaseosa;
    Double volumenMinimo = 0.0;
    Double volumenMaximo = Double.MAX_VALUE;
    Double graduacionAlcoholicaMinima = 0.0;
    Double graduacionAlcoholicaMaxima = 100.0;
}
