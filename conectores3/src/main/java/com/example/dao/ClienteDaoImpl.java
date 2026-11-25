package com.example.dao;

import com.example.database.ConexionBD;
import com.example.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDaoImpl implements IDao<Cliente, Integer> {

    public ClienteDaoImpl() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS clientes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    telefono TEXT NOT NULL
                )
                """;

        try (Statement st = ConexionBD.getConnection().createStatement()) {
            st.execute(sql);
        }
    }

    private Cliente map(ResultSet rs) throws SQLException {

        return new Cliente(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("telefono")
        );
    }

    @Override
    public List<Cliente> findAll() throws SQLException {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM clientes ORDER BY id";

        try (Statement st = ConexionBD.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(map(rs));
            }
        }

        return lista;
    }

    @Override
    public Optional<Cliente> findById(Integer id) throws SQLException {

        String sql = "SELECT * FROM clientes WHERE id = ?";

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

    @Override
    public boolean insert(Cliente c) throws SQLException {

        String sql = """
                INSERT INTO clientes(nombre, email, telefono)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement ps =
                     ConexionBD.getConnection().prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefono());

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean update(Cliente c) throws SQLException {

        String sql = """
                UPDATE clientes
                SET nombre = ?, email = ?, telefono = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps =
                     ConexionBD.getConnection().prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefono());
            ps.setInt(4, c.getId());

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {

        String sql = "DELETE FROM clientes WHERE id = ?";

        try (PreparedStatement ps =
                     ConexionBD.getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() == 1;
        }
    }
}
