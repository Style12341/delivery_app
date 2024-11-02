package deso.delivery_app.persistence.DAO.factories;

import deso.delivery_app.config.Config;
import deso.delivery_app.persistence.DAO.ItemPedidoDAO;
import deso.delivery_app.persistence.memory.ItemPedidoMemory;
import deso.delivery_app.persistence.sql.ItemPedidoSQL;

public class ItemPedidoDAOFactory {
    public static ItemPedidoDAO getDAO() {
        String daoType = Config.getProperty("dao.type");
        if ("database".equalsIgnoreCase(daoType)) {
            // Return database DAO implementation
            return new ItemPedidoSQL();
        } else {
            // Return memory DAO implementation
            return ItemPedidoMemory.getInstance();
        }
    }
}