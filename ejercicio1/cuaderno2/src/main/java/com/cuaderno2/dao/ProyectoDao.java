package com.cuaderno2.dao;

import com.cuaderno2.model.Proyecto;
import com.cuaderno2.utils.ConnectToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDao implements IDao<Proyecto, Integer> {
    private final Connection connection = ConnectToDB.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO proyecto(nombre, ubicacion, id_cliente) VALUES (?,?,?)";
    private static final String SELECT = "SELECT id, nombre, ubicacion, id_cliente FROM proyecto WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, nombre, ubicacion, id_cliente FROM proyecto";
    private static final String UPDATE = "UPDATE proyecto SET nombre=?, ubicacion=?, id_cliente=? WHERE id=?";
    private static final String DELETE = "DELETE FROM proyecto WHERE id=?";

    private void rellenar(PreparedStatement ps, Proyecto p) throws SQLException {
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getUbicacion());
        ps.setInt(3, p.getIdCliente());
    }

    private Proyecto mapear(ResultSet rs) throws SQLException {
        return new Proyecto(rs.getInt("id"), rs.getString("nombre"), rs.getString("ubicacion"), rs.getInt("id_cliente"));
    }

    @Override
    public boolean createRecord(Proyecto p) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            rellenar(ps, p);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Proyecto readRecord(Integer id) {
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
    public boolean updateRecord(Proyecto p, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            rellenar(ps, p);
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
    public List<Proyecto> readRecords() {
        List<Proyecto> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}