package deso.delivery_app.persistence.DAO.factories;

import deso.delivery_app.config.Config;
import deso.delivery_app.persistence.DAO.ClienteDAO;
import deso.delivery_app.persistence.memory.ClienteMemory;
import deso.delivery_app.persistence.sql.ClienteSQL;

public class ClienteDAOFactory {
    public static ClienteDAO getDAO() {
        String daoType = Config.getProperty("dao.type");
        if ("database".equalsIgnoreCase(daoType)) {
            // Return database DAO implementation
            return new ClienteSQL();
        } else {
            // Return memory DAO implementation
            return ClienteMemory.getInstance();
        }
    }
}