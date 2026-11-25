package com.tienda.dao;

import com.tienda.database.ConexionBD;
import com.tienda.model.Venta;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaDaoImpl implements IDao<Venta, Integer> {
    private final Connection connection = ConexionBD.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO venta(id_cliente, id_videojuego, fecha, unidades, importe) VALUES (?,?,?,?,?)";
    private static final String SELECT = "SELECT id, id_cliente, id_videojuego, fecha, unidades, importe FROM venta WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, id_cliente, id_videojuego, fecha, unidades, importe FROM venta";
    private static final String UPDATE = "UPDATE venta SET unidades=?, fecha=?, importe=? * (SELECT precio FROM videojuego WHERE id=venta.id_videojuego) WHERE id=?";
    private static final String DELETE = "DELETE FROM venta WHERE id=?";
    private static final String SELECT_JUEGO = "SELECT precio, stock FROM videojuego WHERE id=?";
    private static final String UPDATE_STOCK = "UPDATE videojuego SET stock = stock - ? WHERE id=?";

    private Venta mapear(ResultSet rs) throws SQLException {
        return new Venta(rs.getInt("id"), rs.getInt("id_cliente"), rs.getInt("id_videojuego"),
                rs.getDate("fecha").toLocalDate(), rs.getInt("unidades"), rs.getDouble("importe"));
    }

    // CREATE = registrar venta con transacción
    @Override
    public boolean createRecord(Venta v) {
        try {
            connection.setAutoCommit(false);                       // empieza la transacción
            double precio = 0;
            int stock = 0;

            try (PreparedStatement ps = connection.prepareStatement(SELECT_JUEGO)) {
                ps.setInt(1, v.getIdVideojuego());
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) throw new SQLException("El videojuego no existe");
                    precio = rs.getDouble("precio");
                    stock = rs.getInt("stock");
                }
            }
            if (stock < v.getUnidades()) throw new SQLException("Stock insuficiente");

            try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
                ps.setInt(1, v.getIdCliente());
                ps.setInt(2, v.getIdVideojuego());
                ps.setDate(3, Date.valueOf(v.getFecha()));
                ps.setInt(4, v.getUnidades());
                ps.setDouble(5, precio * v.getUnidades());
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_STOCK)) {
                ps.setInt(1, v.getUnidades());
                ps.setInt(2, v.getIdVideojuego());
                ps.executeUpdate();
            }

            connection.commit();                                   // todo bien: se confirma
            return true;
        } catch (SQLException e) {
            System.out.println("Error en la venta: " + e.getMessage());
            try { connection.rollback(); } catch (SQLException ex) { System.out.println(ex.getMessage()); }
            return false;
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ex) { }
        }
    }

    @Override
    public Venta readRecord(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return null;
    }

    // UPDATE: cambia unidades y fecha, y recalcula el importe
    @Override
    public boolean updateRecord(Venta v, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setInt(1, v.getUnidades());
            ps.setDate(2, Date.valueOf(v.getFecha()));
            ps.setInt(3, v.getUnidades());
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteRecord(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al borrar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Venta> readRecords() {
        List<Venta> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}