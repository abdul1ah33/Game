package com.example.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // hey dont forget to change password
    private static final String URL = "jdbc:mysql://localhost:3306/game";
    private static final String USER = "root";
    private static final String PASSWORD = "bedo.2005";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
