package deso.delivery_app.controllers;

import deso.delivery_app.persistence.DAO.FiltrosVendedor;

import java.util.Map;

public interface Controller {
    void mostrarLista(FiltrosVendedor fitlros);
    void crear();
    void modificar();
    void eliminar(int id);
    void buscar(int id);
}
