package deso.delivery_app.persistence.DAO;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.models.Categoria;
import deso.delivery_app.models.Pago;

public interface CategoriaDAO {
    Categoria create(Categoria c);

    Categoria get(long idC);

    Categoria update(Categoria c);

    void delete(long idC);

    Categoria getCategoriaByTipoItem(TIPO_ITEM tipo_item);
}
