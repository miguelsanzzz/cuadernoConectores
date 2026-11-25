package com.tienda.dao;

import com.tienda.database.ConexionBD;
import com.tienda.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl implements IDao<Cliente, Integer> {
    private final Connection connection = ConexionBD.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO cliente(nombre, email) VALUES (?,?)";
    private static final String SELECT = "SELECT id, nombre, email FROM cliente WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, nombre, email FROM cliente";
    private static final String UPDATE = "UPDATE cliente SET nombre=?, email=? WHERE id=?";
    private static final String DELETE = "DELETE FROM cliente WHERE id=?";

    @Override
    public boolean createRecord(Cliente c) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
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
                if (rs.next()) return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateRecord(Cliente c, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
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
    public List<Cliente> readRecords() {
        List<Cliente> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email")));
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}