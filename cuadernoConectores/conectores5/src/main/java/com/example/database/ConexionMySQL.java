
package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static ConexionMySQL instancia;
    private Connection conexion;

    private static final String URL =
        "jdbc:mysql://localhost:3306/tienda_videojuegos";

    private static final String USUARIO = "root";

    // Cambia la contraseña si tu MySQL tiene una.
    private static final String PASSWORD = "";

    private ConexionMySQL() {
        conectar();
    }

    private void conectar() {
        try {
            conexion = DriverManager.getConnection(
                URL, USUARIO, PASSWORD
            );

            System.out.println("Conectado a MySQL.");

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error de conexión MySQL: " + e.getMessage(), e
            );
        }
    }

    public static synchronized ConexionMySQL getInstancia() {

        if (instancia == null) {
            instancia = new ConexionMySQL();
        }

        return instancia;
    }

    public synchronized Connection getConexion() {

        try {
            if (conexion == null || conexion.isClosed()) {
                conectar();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conexion;
    }

    public void cerrarConexion() {

        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
