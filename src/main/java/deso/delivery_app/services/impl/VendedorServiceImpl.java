package deso.delivery_app.services.impl;


import deso.delivery_app.persistance.models.Vendedor;
import deso.delivery_app.persistance.repository.VendedorRepository;
import deso.delivery_app.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorServiceImpl implements VendedorService {
    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> getAllVendedores() {
        return vendedorRepository.findAll();
    }

    public List<Vendedor> getMatchingVendedores(Vendedor vendedorFilter) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        return vendedorRepository.findAll(Example.of(vendedorFilter, matcher));
    }

    public Vendedor getVendedorById(Long id) {
        return vendedorRepository.findById(id).orElse(null);
    }

    public Vendedor createVendedor(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public Vendedor updateVendedor(Long id, Vendedor vendedor) {
        if (vendedorRepository.existsById(id)) {
            vendedor.setId(id);
            return vendedorRepository.save(vendedor);
        } else {
            return null;
        }
    }

    public void deleteVendedor(Long id) {
        vendedorRepository.deleteById(id);
    }

}
