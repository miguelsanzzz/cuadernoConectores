package com.cuaderno2.dao;

import com.cuaderno2.model.Empleado;
import com.cuaderno2.utils.ConnectToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDao implements IDao<Empleado, Integer> {
    private final Connection connection = ConnectToDB.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO empleado(id_departamento, id_proyecto, nombre, apellidos, dni, salario) VALUES (?,?,?,?,?,?)";
    private static final String SELECT = "SELECT id, id_departamento, id_proyecto, nombre, apellidos, dni, salario FROM empleado WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, id_departamento, id_proyecto, nombre, apellidos, dni, salario FROM empleado";
    private static final String UPDATE = "UPDATE empleado SET id_departamento=?, id_proyecto=?, nombre=?, apellidos=?, dni=?, salario=? WHERE id=?";
    private static final String DELETE = "DELETE FROM empleado WHERE id=?";

    private void rellenar(PreparedStatement ps, Empleado e) throws SQLException {
        ps.setInt(1, e.getIdDepartamento());
        ps.setInt(2, e.getIdProyecto());
        ps.setString(3, e.getNombre());
        ps.setString(4, e.getApellidos());
        ps.setString(5, e.getDni());
        ps.setDouble(6, e.getSalario());
    }

    private Empleado mapear(ResultSet rs) throws SQLException {
        return new Empleado(rs.getInt("id"), rs.getInt("id_departamento"), rs.getInt("id_proyecto"),
                rs.getString("nombre"), rs.getString("apellidos"), rs.getString("dni"), rs.getDouble("salario"));
    }

    @Override
    public boolean createRecord(Empleado e) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            rellenar(ps, e);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al crear: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Empleado readRecord(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateRecord(Empleado e, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            rellenar(ps, e);
            ps.setInt(7, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteRecord(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al borrar: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Empleado> readRecords() {
        List<Empleado> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException ex) {
            System.out.println("Error al listar: " + ex.getMessage());
        }
        return lista;
    }
}