package deso.delivery_app.services;

import deso.delivery_app.dto.ComidaFilterDTO;
import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Comida;

import java.util.List;

public interface ComidaService {
    public List<Comida> getAllComidas();

    public List<Comida> getMatchingComidas(ItemMenuFilterDTO itemMenuFilter);

    public Comida createComida(Comida comida);

    public Comida updateComida(Long id, Comida comida);
}
