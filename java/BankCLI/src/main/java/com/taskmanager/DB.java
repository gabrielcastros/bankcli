package com.taskmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection connectDB() throws SQLException {
        try {
            var jdbcUrl = DatabaseConfig.getDbUrl();
            var user = DatabaseConfig.getUsername();
            var password = DatabaseConfig.getPassword();

            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}
