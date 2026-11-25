package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

    private static final String URL =
            "jdbc:sqlite:tienda_videojuegos.db";

    private static Connection conexion;

    private ConexionBD() {
    }

    public static synchronized Connection getConnection()
            throws SQLException {

        if (conexion == null || conexion.isClosed()) {

            conexion = DriverManager.getConnection(URL);

            try (Statement st = conexion.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON");
            }
        }

        return conexion;
    }

    public static synchronized void cerrarConexion() {

        if (conexion != null) {

            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }

            } catch (SQLException e) {
                System.out.println(
                        "Error al cerrar la conexión: " + e.getMessage()
                );

            } finally {
                conexion = null;
            }
        }
    }
}
