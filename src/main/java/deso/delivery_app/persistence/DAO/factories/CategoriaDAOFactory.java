package deso.delivery_app.persistence.DAO.factories;

import deso.delivery_app.config.Config;
import deso.delivery_app.persistence.DAO.CategoriaDAO;
import deso.delivery_app.persistence.DAO.ClienteDAO;
import deso.delivery_app.persistence.memory.ClienteMemory;
import deso.delivery_app.persistence.sql.CategoriaSQL;
import deso.delivery_app.persistence.sql.ClienteSQL;

public class CategoriaDAOFactory {
    public static CategoriaDAO getDAO() {
        String daoType = Config.getProperty("dao.type");
        if ("database".equalsIgnoreCase(daoType)) {
            // Return database DAO implementation
            return new CategoriaSQL();
        } else {
            // Return memory DAO implementation
            return null;
        }
    }
}
