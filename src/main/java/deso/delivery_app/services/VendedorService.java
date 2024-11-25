package deso.delivery_app.services;

import deso.delivery_app.persistance.models.Vendedor;

import java.util.List;

public interface VendedorService {

    public List<Vendedor> getAllVendedores();

    public List<Vendedor> getMatchingVendedores(Vendedor vendedorFilter);

    public Vendedor getVendedorById(Long id);

    public Vendedor createVendedor(Vendedor vendedor);

    public Vendedor updateVendedor(Long id, Vendedor vendedor);

    public void deleteVendedor(Long id);
}
