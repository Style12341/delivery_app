package deso.delivery_app.persistence.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlUtils {

    public static long fetchId(int rowsAffected, Statement stmt) throws SQLException {
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (rowsAffected > 0) {
            // Retrieve the auto-generated keys (insert ID)
            if (generatedKeys.next()) {
                long insertId = generatedKeys.getLong(1);
                return insertId;
            } else {
                System.out.println("Failed to retrieve client ID.");
            }
        } else {
            System.out.println("No client inserted.");
        }
        return -1;
    }
}
