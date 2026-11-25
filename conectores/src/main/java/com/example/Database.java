package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Path;


public class Database {

    // Ruta de nuestra base de datos
    private static final String URL = "jdbc:sqlite:act01.db";

    // Metodo para conectar con SQLite
    public static Connection conectar() throws SQLException {

        Connection conexion = DriverManager.getConnection(URL);

        // Activar las claves externas
        try (Statement st = conexion.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }

        return conexion;
    }

    // Crear las tablas leyendo el archivo SQL
    public static void inicializar() {

        try {

            String contenido = Files.readString(
                    Path.of("act01.sql")
            );

            try (Connection conexion = conectar();
                 Statement st = conexion.createStatement()) {

                String[] instrucciones = contenido.split(";");

                for (String instruccion : instrucciones) {

                    if (!instruccion.isBlank()) {
                        st.execute(instruccion);
                    }
                }
            }

            System.out.println("Base de datos preparada correctamente.");

        } catch (Exception e) {

            System.out.println("Error al preparar la base de datos: "
                    + e.getMessage());
        }
    }
}
