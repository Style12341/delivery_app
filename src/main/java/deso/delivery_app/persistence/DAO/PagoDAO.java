package deso.delivery_app.persistence.DAO;


import deso.delivery_app.models.Pago;


public interface PagoDAO {
    Pago create(Pago p);

    Pago get(long idP);

    Pago update(Pago p);

    void delete(long idP);
}
