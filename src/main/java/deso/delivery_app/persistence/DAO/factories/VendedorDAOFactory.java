package deso.delivery_app.persistence.DAO.factories;

import deso.delivery_app.config.Config;
import deso.delivery_app.persistence.DAO.VendedorDAO;
import deso.delivery_app.persistence.memory.VendedorMemory;
import deso.delivery_app.persistence.sql.VendedorSQL;

public class VendedorDAOFactory {
    public static VendedorDAO getDAO() {
        String daoType = Config.getProperty("dao.type");
        if ("database".equalsIgnoreCase(daoType)) {
            // Return database DAO implementation
            return new VendedorSQL();
        } else {
            // Return memory DAO implementation
            return VendedorMemory.getInstance();
        }
    }
}