package deso.delivery_app.persistence.DAO.factories;

import deso.delivery_app.config.Config;
import deso.delivery_app.persistence.DAO.PagoDAO;
import deso.delivery_app.persistence.sql.PagoSQL;

public class PagoDAOFactory {
    public static PagoDAO getDAO() {
        String daoType = Config.getProperty("dao.type");
        if ("database".equalsIgnoreCase(daoType)) {
            // Return database DAO implementation
            return new PagoSQL();
        } else {
            // Return memory DAO implementation
            return null;
        }
    }
}
