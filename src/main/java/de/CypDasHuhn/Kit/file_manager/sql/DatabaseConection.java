package de.CypDasHuhn.Kit.file_manager.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConection {
    public static Connection getConnect(String url, String userName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
