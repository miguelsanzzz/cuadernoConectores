package com.tienda.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionBD {
    private static final ConexionBD INSTANCE = new ConexionBD();
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/tienda_videojuegos";
    private static final String USER = "root";
    private static final String PASS = "root";

    private ConexionBD() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conectado a tienda_videojuegos");
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    public static ConexionBD getInstance() { return INSTANCE; }

    public Connection getConnection() { return connection; }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            System.err.println("Error cerrando conexión: " + e.getMessage());
        }
    }
}