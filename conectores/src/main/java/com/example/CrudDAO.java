package com.example;
import java.sql.*;
import java.util.Scanner;

public class CrudDAO {

    private final String tabla;
    private final String[] columnas;

    public CrudDAO(String tabla, String[] columnas) {
        this.tabla = tabla;
        this.columnas = columnas;
    }

    // READ: consultar todos los registros
    public void listar() {

        String sql = "SELECT * FROM " + tabla;

        try (Connection con = Database.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            ResultSetMetaData datos = rs.getMetaData();
            int total = datos.getColumnCount();

            boolean hayDatos = false;

            while (rs.next()) {

                hayDatos = true;

                for (int i = 1; i <= total; i++) {
                    System.out.print(datos.getColumnName(i)
                            + ": " + rs.getString(i) + " | ");
                }

                System.out.println();
            }

            if (!hayDatos) {
                System.out.println("No hay registros.");
            }

        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
    }

    // READ: buscar un registro por ID
    public void buscar(int id) {

        String sql = "SELECT * FROM " + tabla + " WHERE id = ?";

        try (Connection con = Database.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ResultSetMetaData datos = rs.getMetaData();

                    for (int i = 1; i <= datos.getColumnCount(); i++) {
                        System.out.println(datos.getColumnName(i)
                                + ": " + rs.getString(i));
                    }

                } else {
                    System.out.println("Registro no encontrado.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
    }

    // CREATE: insertar un registro
    public void insertar(Scanner teclado) {

        StringBuilder sql = new StringBuilder(
                "INSERT INTO " + tabla + " (");

        for (int i = 0; i < columnas.length; i++) {

            sql.append(columnas[i]);

            if (i < columnas.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(") VALUES (");

        sql.append("?,".repeat(columnas.length));
        sql.setLength(sql.length() - 1);
        sql.append(")");

        try (Connection con = Database.conectar();
             PreparedStatement ps =
                     con.prepareStatement(sql.toString())) {

            for (int i = 0; i < columnas.length; i++) {

                System.out.print("Introduce "
                        + columnas[i] + ": ");

                String valor = teclado.nextLine();

                if (valor.isBlank()) {
                    ps.setNull(i + 1, Types.NULL);
                } else {
                    ps.setString(i + 1, valor);
                }
            }

            int filas = ps.executeUpdate();

            System.out.println("Registros insertados: " + filas);

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    // UPDATE: modificar un registro
    public void modificar(int id, Scanner teclado) {

        StringBuilder sql = new StringBuilder(
                "UPDATE " + tabla + " SET ");

        for (int i = 0; i < columnas.length; i++) {

            sql.append(columnas[i]).append(" = ?");

            if (i < columnas.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(" WHERE id = ?");

        try (Connection con = Database.conectar();
             PreparedStatement ps =
                     con.prepareStatement(sql.toString())) {

            for (int i = 0; i < columnas.length; i++) {

                System.out.print("Nuevo valor de "
                        + columnas[i] + ": ");

                String valor = teclado.nextLine();

                if (valor.isBlank()) {
                    ps.setNull(i + 1, Types.NULL);
                } else {
                    ps.setString(i + 1, valor);
                }
            }

            ps.setInt(columnas.length + 1, id);

            int filas = ps.executeUpdate();

            System.out.println("Registros modificados: " + filas);

        } catch (SQLException e) {
            System.out.println("Error al modificar: " + e.getMessage());
        }
    }

    // DELETE: eliminar un registro
    public void eliminar(int id) {

        String sql = "DELETE FROM " + tabla + " WHERE id = ?";

        try (Connection con = Database.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            System.out.println("Registros eliminados: " + filas);

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}
