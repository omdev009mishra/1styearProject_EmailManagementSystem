package utils;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/emaildb";
    private static final String USER = "root"; // Change this to your MySQL user
    private static final String PASSWORD = "password"; // Change this to your password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}