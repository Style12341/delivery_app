package deso.delivery_app.persistence.DAO.factories;

import deso.delivery_app.config.Config;
import deso.delivery_app.persistence.DAO.PedidoDAO;
import deso.delivery_app.persistence.memory.PedidoMemory;
import deso.delivery_app.persistence.sql.PedidoSQL;

public class PedidoDAOFactory {
    public static PedidoDAO getDAO() {
        String daoType = Config.getProperty("dao.type");
        if ("database".equalsIgnoreCase(daoType)) {
            // Return database DAO implementation
            return new PedidoSQL();
        } else {
            // Return memory DAO implementation
            return PedidoMemory.getInstance();
        }
    }
}