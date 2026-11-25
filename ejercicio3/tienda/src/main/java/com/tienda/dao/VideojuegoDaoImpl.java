package com.tienda.dao;

import com.tienda.database.ConexionBD;
import com.tienda.model.Videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoDaoImpl implements IDao<Videojuego, Integer> {
    private final Connection connection = ConexionBD.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO videojuego(titulo, plataforma, precio, stock) VALUES (?,?,?,?)";
    private static final String SELECT = "SELECT id, titulo, plataforma, precio, stock FROM videojuego WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, titulo, plataforma, precio, stock FROM videojuego";
    private static final String UPDATE = "UPDATE videojuego SET titulo=?, plataforma=?, precio=?, stock=? WHERE id=?";
    private static final String DELETE = "DELETE FROM videojuego WHERE id=?";

    private Videojuego mapear(ResultSet rs) throws SQLException {
        return new Videojuego(rs.getInt("id"), rs.getString("titulo"), rs.getString("plataforma"),
                rs.getDouble("precio"), rs.getInt("stock"));
    }

    @Override
    public boolean createRecord(Videojuego v) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, v.getTitulo());
            ps.setString(2, v.getPlataforma());
            ps.setDouble(3, v.getPrecio());
            ps.setInt(4, v.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Videojuego readRecord(Integer id) {
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

    @Override
    public boolean updateRecord(Videojuego v, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, v.getTitulo());
            ps.setString(2, v.getPlataforma());
            ps.setDouble(3, v.getPrecio());
            ps.setInt(4, v.getStock());
            ps.setInt(5, id);
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
    public List<Videojuego> readRecords() {
        List<Videojuego> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}