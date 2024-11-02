package deso.delivery_app.persistence.DAO.factories;

import deso.delivery_app.config.Config;
import deso.delivery_app.persistence.DAO.ItemMenuDAO;
import deso.delivery_app.persistence.memory.ItemMenuMemory;
import deso.delivery_app.persistence.sql.ItemMenuSQL;

public class ItemMenuDAOFactory {
    public static ItemMenuDAO getDAO() {
        String daoType = Config.getProperty("dao.type");
        if ("database".equalsIgnoreCase(daoType)) {
            // Return database DAO implementation
            return new ItemMenuSQL();
        } else {
            // Return memory DAO implementation
            return ItemMenuMemory.getInstance();
        }
    }
}