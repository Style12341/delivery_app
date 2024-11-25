package deso.delivery_app.services;

import deso.delivery_app.dto.BebidaFilterDTO;
import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Bebida;

import java.util.List;

public interface BebidaService {
    public List<Bebida> getAllBebidas();

    public List<Bebida> getMatchingBebidas(ItemMenuFilterDTO itemMenuFilter);

    public Bebida createBebida(Bebida bebida);

    public Bebida updateBebida(Long id, Bebida bebida);
}
