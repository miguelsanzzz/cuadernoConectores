
package com.example.dao;

import com.example.database.ConexionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class OperacionesAvanzadasDao {

    private Connection getConexion() {
        return ConexionMySQL.getInstancia().getConexion();
    }

    // ==========================================
    // 1. REGISTRAR VENTA CON TRANSACCION
    // ==========================================

    public boolean registrarVenta(
            int idCliente,
            int idVideojuego,
            int cantidad) {

        Connection con = getConexion();

        try {

            con.setAutoCommit(false);

            // Comprobar que existe el videojuego.
            String consulta =
                "SELECT precio, stock FROM videojuegos " +
                "WHERE id = ? FOR UPDATE";

            double precio;
            int stock;

            try (PreparedStatement ps =
                    con.prepareStatement(consulta)) {

                ps.setInt(1, idVideojuego);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        throw new SQLException(
                            "El videojuego no existe."
                        );
                    }

                    precio = rs.getDouble("precio");
                    stock = rs.getInt("stock");
                }
            }

            // Comprobar que existe el cliente.
            String consultaCliente =
                "SELECT id FROM clientes WHERE id = ?";

            try (PreparedStatement ps =
                    con.prepareStatement(consultaCliente)) {

                ps.setInt(1, idCliente);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        throw new SQLException(
                            "El cliente no existe."
                        );
                    }
                }
            }

            // Comprobar cantidad.
            if (cantidad <= 0) {
                throw new SQLException(
                    "La cantidad debe ser mayor que cero."
                );
            }

            // Comprobar stock.
            if (stock < cantidad) {
                throw new SQLException(
                    "No hay stock suficiente. Disponible: " + stock
                );
            }

            // Registrar venta.
            String insertar =
                "INSERT INTO ventas " +
                "(cliente, videojuego, cantidad, precio, fecha) " +
                "VALUES (?, ?, ?, ?, CURDATE())";

            try (PreparedStatement ps =
                    con.prepareStatement(insertar)) {

                ps.setInt(1, idCliente);
                ps.setInt(2, idVideojuego);
                ps.setInt(3, cantidad);
                ps.setDouble(4, precio);

                ps.executeUpdate();
            }

            // Actualizar stock.
            String actualizar =
                "UPDATE videojuegos " +
                "SET stock = stock - ? WHERE id = ?";

            try (PreparedStatement ps =
                    con.prepareStatement(actualizar)) {

                ps.setInt(1, cantidad);
                ps.setInt(2, idVideojuego);

                if (ps.executeUpdate() != 1) {
                    throw new SQLException(
                        "No se pudo actualizar el stock."
                    );
                }
            }

            // Confirmar todos los cambios.
            con.commit();

            System.out.println("Venta registrada correctamente.");

            System.out.printf(
                "Importe de la venta: %.2f euros%n",
                precio * cantidad
            );

            return true;

        } catch (SQLException e) {

            try {
                con.rollback();
                System.out.println(
                    "ROLLBACK: se han deshecho los cambios."
                );
            } catch (SQLException error) {
                System.out.println(
                    "Error al deshacer cambios: " + error.getMessage()
                );
            }

            System.out.println("Error: " + e.getMessage());

            return false;

        } finally {

            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ==========================================
    // 2. CONSULTAR VENTAS DE UN CLIENTE
    // ==========================================

    public void consultarVentasCliente(int idCliente) {

        String sql = "{CALL consultarVentasCliente(?)}";

        try (CallableStatement cs =
                getConexion().prepareCall(sql)) {

            cs.setInt(1, idCliente);

            try (ResultSet rs = cs.executeQuery()) {

                boolean encontrado = false;

                while (rs.next()) {

                    encontrado = true;

                    System.out.println("\n------------------------");
                    System.out.println(
                        "ID venta: " +
                        rs.getInt("identificador_venta")
                    );

                    System.out.println(
                        "Videojuego: " +
                        rs.getString("videojuego")
                    );

                    System.out.println(
                        "Fecha: " +
                        rs.getDate("fecha")
                    );

                    System.out.println(
                        "Unidades: " +
                        rs.getInt("unidades")
                    );

                    System.out.println(
                        "Importe: " +
                        rs.getBigDecimal("importe") + " euros"
                    );
                }

                if (!encontrado) {
                    System.out.println(
                        "El cliente no tiene ventas registradas."
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(
                "Error consultando ventas: " + e.getMessage()
            );
        }
    }

    // ==========================================
    // 3. CONSULTAR TOTAL GASTADO POR UN CLIENTE
    // ==========================================

    public double obtenerTotalGastado(int idCliente) {

        String sql = "{CALL totalGastadoCliente(?, ?)}";

        try (CallableStatement cs =
                getConexion().prepareCall(sql)) {

            cs.setInt(1, idCliente);

            cs.registerOutParameter(2, Types.DECIMAL);

            cs.execute();

            java.math.BigDecimal total = cs.getBigDecimal(2);

            return total == null ? 0 : total.doubleValue();

        } catch (SQLException e) {

            System.out.println(
                "Error calculando el total: " + e.getMessage()
            );

            return 0;
        }
    }
}

