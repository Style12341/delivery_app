package deso.delivery_app.services.impl;

import deso.delivery_app.dto.ComidaFilterDTO;
import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Comida;
import deso.delivery_app.persistance.repository.ComidaRepository;
import deso.delivery_app.services.ComidaService;
import deso.delivery_app.specifications.ComidaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComidaServiceImpl implements ComidaService {
    @Autowired
    private ComidaRepository comidaRepository;

    @Override
    public List<Comida> getAllComidas() {
        return comidaRepository.findAll();
    }

    @Override
    public List<Comida> getMatchingComidas(ItemMenuFilterDTO itemFilter) {
        Specification<Comida> spec =  new ComidaSpecification().fromFilter(itemFilter);
        return comidaRepository.findAll(spec);
    }

    @Override
    public Comida createComida(Comida comida) {
        return comidaRepository.save(comida);
    }

    @Override
    public Comida updateComida(Long id, Comida comida) {
        if (comidaRepository.existsById(id)) {
            comida.setId(id); // Ensure the ID is set to the provided ID
            return comidaRepository.save(comida);
        } else {
            // Handle the case where the Cliente does not exist
            return null; // Or throw an exception
        }
    }
}
