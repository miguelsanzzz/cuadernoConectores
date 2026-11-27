package com.cuaderno2.dao;

import com.cuaderno2.model.Cliente;
import com.cuaderno2.utils.ConnectToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao implements IDao<Cliente, Integer> {
    private final Connection connection = ConnectToDB.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO clientes(nombre, persona_contacto, tfno_contacto, cif) VALUES (?,?,?,?)";
    private static final String SELECT = "SELECT id, nombre, persona_contacto, tfno_contacto, cif FROM clientes WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, nombre, persona_contacto, tfno_contacto, cif FROM clientes";
    private static final String UPDATE = "UPDATE clientes SET nombre=?, persona_contacto=?, tfno_contacto=?, cif=? WHERE id=?";
    private static final String DELETE = "DELETE FROM clientes WHERE id=?";

    private void rellenar(PreparedStatement ps, Cliente c) throws SQLException {
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getPersonaContacto());
        ps.setString(3, c.getTfnoContacto());
        ps.setString(4, c.getCif());
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("persona_contacto"),
                rs.getString("tfno_contacto"), rs.getString("cif"));
    }

    @Override
    public boolean createRecord(Cliente c) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            rellenar(ps, c);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cliente readRecord(Integer id) {
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
    public boolean updateRecord(Cliente c, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            rellenar(ps, c);
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
    public List<Cliente> readRecords() {
        List<Cliente> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}