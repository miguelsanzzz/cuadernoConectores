package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionBD {
    private static final String BBDD = "H2";   

    private static final ConexionBD INSTANCE = new ConexionBD();
    private Connection connection;

    private ConexionBD() {
        String url, user, pass;
        switch (BBDD) {
            case "DERBY":  url = "jdbc:derby:exampleDerby;create=true"; user = ""; pass = ""; break;
            case "HSQLDB": url = "jdbc:hsqldb:file:./exampleHSQL"; user = "SA"; pass = ""; break;
            default:       url = "jdbc:h2:./exampleH2"; user = "sa"; pass = "";
        }
        try {
            connection = user.isEmpty() ? DriverManager.getConnection(url)
                                        : DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado a " + BBDD);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    public static ConexionBD getInstance() { return INSTANCE; }

    public Connection getConnection() { return connection; }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                if (BBDD.equals("HSQLDB")) connection.createStatement().execute("SHUTDOWN");
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error cerrando conexión: " + e.getMessage());
        }
    }
}
