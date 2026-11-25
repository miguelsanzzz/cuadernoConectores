package com.example.dao;

import com.example.database.ConexionBD;
import com.example.model.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VentaDaoImpl implements IDao<Venta, Integer> {

    public VentaDaoImpl() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS ventas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    cliente_id INTEGER NOT NULL,
                    videojuego_id INTEGER NOT NULL,
                    cantidad INTEGER NOT NULL CHECK(cantidad > 0),
                    precio_unitario REAL NOT NULL,
                    fecha TEXT NOT NULL,
                    FOREIGN KEY(cliente_id) REFERENCES clientes(id),
                    FOREIGN KEY(videojuego_id) REFERENCES videojuegos(id)
                )
                """;

        try (Statement st = ConexionBD.getConnection().createStatement()) {
            st.execute(sql);
        }
    }

    private Venta map(ResultSet rs) throws SQLException {

        return new Venta(
                rs.getInt("id"),
                rs.getInt("cliente_id"),
                rs.getInt("videojuego_id"),
                rs.getInt("cantidad"),
                rs.getDouble("precio_unitario"),
                rs.getString("fecha")
        );
    }

    @Override
    public List<Venta> findAll() throws SQLException {

        List<Venta> lista = new ArrayList<>();

        String sql = "SELECT * FROM ventas ORDER BY id";

        try (Statement st = ConexionBD.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(map(rs));
            }
        }

        return lista;
    }

    @Override
    public Optional<Venta> findById(Integer id) throws SQLException {

        String sql = "SELECT * FROM ventas WHERE id = ?";

        try (PreparedStatement ps =
                     ConexionBD.getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        }

        return Optional.empty();
    }

    // REGISTRAR VENTA CON TRANSACCIÓN

    @Override
    public boolean insert(Venta v) throws SQLException {

        Connection con = ConexionBD.getConnection();
        boolean autoCommitAnterior = con.getAutoCommit();

        try {

            con.setAutoCommit(false);

            double precio = v.getPrecioUnitario();

            // Comprobar que existe el videojuego y que hay stock.
            String comprobar = """
                    SELECT stock, precio
                    FROM videojuegos
                    WHERE id = ?
                    """;

            try (PreparedStatement ps = con.prepareStatement(comprobar)) {

                ps.setInt(1, v.getVideojuegoId());

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        throw new SQLException("El videojuego no existe.");
                    }

                    if (rs.getInt("stock") < v.getCantidad()) {
                        throw new SQLException("No hay stock suficiente.");
                    }

                    if (precio <= 0) {
                        precio = rs.getDouble("precio");
                    }
                }
            }

            // Descontar el stock.
            String descontar = """
                    UPDATE videojuegos
                    SET stock = stock - ?
                    WHERE id = ? AND stock >= ?
                    """;

            try (PreparedStatement ps = con.prepareStatement(descontar)) {

                ps.setInt(1, v.getCantidad());
                ps.setInt(2, v.getVideojuegoId());
                ps.setInt(3, v.getCantidad());

                if (ps.executeUpdate() != 1) {
                    throw new SQLException("No se pudo actualizar el stock.");
                }
            }

            // Insertar la venta.
            String insertar = """
                    INSERT INTO ventas(
                        cliente_id, videojuego_id, cantidad,
                        precio_unitario, fecha
                    )
                    VALUES (?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement ps = con.prepareStatement(insertar)) {

                ps.setInt(1, v.getClienteId());
                ps.setInt(2, v.getVideojuegoId());
                ps.setInt(3, v.getCantidad());
                ps.setDouble(4, precio);
                ps.setString(5, v.getFecha());

                ps.executeUpdate();
            }

            // Confirmar transacción.
            con.commit();

            return true;

        } catch (Exception e) {

            // Deshacer los cambios si algo falla.
            try {
                con.rollback();
            } catch (SQLException error) {
                e.addSuppressed(error);
            }

            if (e instanceof SQLException) {
                throw (SQLException) e;
            }

            throw new SQLException("Error al registrar la venta.", e);

        } finally {

            con.setAutoCommit(autoCommitAnterior);
        }
    }

    // MODIFICAR VENTA Y ACTUALIZAR STOCK

    @Override
    public boolean update(Venta v) throws SQLException {

        Connection con = ConexionBD.getConnection();
        boolean autoCommitAnterior = con.getAutoCommit();

        try {

            con.setAutoCommit(false);

            Venta anterior;

            String buscar = "SELECT * FROM ventas WHERE id = ?";

            try (PreparedStatement ps = con.prepareStatement(buscar)) {

                ps.setInt(1, v.getId());

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        con.rollback();
                        return false;
                    }

                    anterior = map(rs);
                }
            }

            // Devolver al stock la cantidad de la venta anterior.
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE videojuegos SET stock = stock + ? WHERE id = ?")) {

                ps.setInt(1, anterior.getCantidad());
                ps.setInt(2, anterior.getVideojuegoId());

                if (ps.executeUpdate() != 1) {
                    throw new SQLException("No se pudo devolver el stock.");
                }
            }

            double precio = v.getPrecioUnitario();

            // Comprobar el nuevo videojuego y su stock.
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT stock, precio FROM videojuegos WHERE id = ?")) {

                ps.setInt(1, v.getVideojuegoId());

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        throw new SQLException("El videojuego no existe.");
                    }

                    if (rs.getInt("stock") < v.getCantidad()) {
                        throw new SQLException("No hay stock suficiente.");
                    }

                    if (precio <= 0) {
                        precio = rs.getDouble("precio");
                    }
                }
            }

            // Descontar el nuevo stock.
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE videojuegos SET stock = stock - ? WHERE id = ? AND stock >= ?")) {

                ps.setInt(1, v.getCantidad());
                ps.setInt(2, v.getVideojuegoId());
                ps.setInt(3, v.getCantidad());

                if (ps.executeUpdate() != 1) {
                    throw new SQLException("No se pudo descontar el stock.");
                }
            }

            // Actualizar los datos de la venta.
            String actualizar = """
                    UPDATE ventas
                    SET cliente_id = ?, videojuego_id = ?,
                        cantidad = ?, precio_unitario = ?, fecha = ?
                    WHERE id = ?
                    """;

            try (PreparedStatement ps = con.prepareStatement(actualizar)) {

                ps.setInt(1, v.getClienteId());
                ps.setInt(2, v.getVideojuegoId());
                ps.setInt(3, v.getCantidad());
                ps.setDouble(4, precio);
                ps.setString(5, v.getFecha());
                ps.setInt(6, v.getId());

                ps.executeUpdate();
            }

            con.commit();

            return true;

        } catch (Exception e) {

            try {
                con.rollback();
            } catch (SQLException error) {
                e.addSuppressed(error);
            }

            if (e instanceof SQLException) {
                throw (SQLException) e;
            }

            throw new SQLException("Error al modificar la venta.", e);

        } finally {

            con.setAutoCommit(autoCommitAnterior);
        }
    }

    // ELIMINAR VENTA Y DEVOLVER STOCK

    @Override
    public boolean delete(Integer id) throws SQLException {

        Connection con = ConexionBD.getConnection();
        boolean autoCommitAnterior = con.getAutoCommit();

        try {

            con.setAutoCommit(false);

            Venta venta;

            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM ventas WHERE id = ?")) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        con.rollback();
                        return false;
                    }

                    venta = map(rs);
                }
            }

            // Devolver las unidades al stock.
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE videojuegos SET stock = stock + ? WHERE id = ?")) {

                ps.setInt(1, venta.getCantidad());
                ps.setInt(2, venta.getVideojuegoId());

                ps.executeUpdate();
            }

            // Eliminar la venta.
            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM ventas WHERE id = ?")) {

                ps.setInt(1, id);

                if (ps.executeUpdate() != 1) {
                    throw new SQLException("No se pudo eliminar la venta.");
                }
            }

            con.commit();

            return true;

        } catch (Exception e) {

            try {
                con.rollback();
            } catch (SQLException error) {
                e.addSuppressed(error);
            }

            if (e instanceof SQLException) {
                throw (SQLException) e;
            }

            throw new SQLException("Error al eliminar la venta.", e);

        } finally {

            con.setAutoCommit(autoCommitAnterior);
        }
    }
}
