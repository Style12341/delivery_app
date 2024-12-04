package deso.delivery_app.services.impl;

import deso.delivery_app.dto.ItemMenuFilterDTO;
import deso.delivery_app.persistance.models.Bebida;
import deso.delivery_app.persistance.repository.BebidaRepository;
import deso.delivery_app.services.BebidaService;
import deso.delivery_app.specifications.BebidaSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BebidaServiceImpl implements BebidaService {
    private final BebidaRepository bebidaRepository;

    public BebidaServiceImpl(BebidaRepository bebidaRepository) {
        this.bebidaRepository = bebidaRepository;
    }

    @Override
    public List<Bebida> getAllBebidas() {
        return bebidaRepository.findAll();
    }

    @Override
    public List<Bebida> getMatchingBebidas(ItemMenuFilterDTO itemMenu) {
        Specification<Bebida> spec = new BebidaSpecification().fromFilter(itemMenu);
        return bebidaRepository.findAll(spec);
    }

    @Override
    public Bebida createBebida(Bebida bebida) {
        return bebidaRepository.save(bebida);
    }

    @Override
    public Bebida updateBebida(Long id, Bebida bebida) {
        if (bebidaRepository.existsById(id)) {
            bebida.setId(id); // Ensure the ID is set to the provided ID
            return bebidaRepository.save(bebida);
        } else {
            // Handle the case where the Cliente does not exist
            return null; // Or throw an exception
        }
    }
}
