package com.cuaderno2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConnectToDB {
    private static final ConnectToDB INSTANCE = new ConnectToDB();
    private Connection connection;

    private static final String URL = "jdbc:sqlite:act01.db";

    private ConnectToDB() {
        try {
            connection = DriverManager.getConnection(URL);
            try (Statement st = connection.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON");
            }
            System.out.println("Conectado a act01.db");
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    public static ConnectToDB getInstance() { return INSTANCE; }

    public Connection getConnection() { return connection; }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            System.err.println("Error cerrando conexión: " + e.getMessage());
        }
    }
}