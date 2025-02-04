package com.dbs.bgcp.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSQLConnectionTest {
    public static void main(String[] args) {
        // Define your connection string
        String url = "jdbc:sqlserver://192.168.1.32:1433;encrypt=true;TrustServerCertificate=true;databaseName=CLS_WAA";
        String username = "sqluser";
        String password = "Admin$345";

        // Test the connection
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (connection != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }
    }
}
