package deso.delivery_app.persistence;

import deso.delivery_app.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
    private static Connection con = null;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = Config.getProperty("db.username");
    private static final String password = Config.getProperty("db.password");
    private static final String url = Config.getProperty("db.url");

    private DBConnector() {
    }

    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName(driver);
                con = (Connection) DriverManager.getConnection(url, user, password);
                if (con != null)
                    System.out.println("Connected to DB");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return con;
    }

}
