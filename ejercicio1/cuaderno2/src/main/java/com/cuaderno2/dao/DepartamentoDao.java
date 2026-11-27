package com.cuaderno2.dao;

import com.cuaderno2.model.Departamento;
import com.cuaderno2.utils.ConnectToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDao implements IDao<Departamento, Integer> {
    private final Connection connection = ConnectToDB.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO departamento(nombre, ubicacion) VALUES (?,?)";
    private static final String SELECT = "SELECT id, nombre, ubicacion FROM departamento WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, nombre, ubicacion FROM departamento";
    private static final String UPDATE = "UPDATE departamento SET nombre=?, ubicacion=? WHERE id=?";
    private static final String DELETE = "DELETE FROM departamento WHERE id=?";

    private Departamento mapear(ResultSet rs) throws SQLException {
        return new Departamento(rs.getInt("id"), rs.getString("nombre"), rs.getString("ubicacion"));
    }

    @Override
    public boolean createRecord(Departamento d) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getUbicacion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Departamento readRecord(Integer id) {
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
    public boolean updateRecord(Departamento d, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getUbicacion());
            ps.setInt(3, id);
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
    public List<Departamento> readRecords() {
        List<Departamento> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}