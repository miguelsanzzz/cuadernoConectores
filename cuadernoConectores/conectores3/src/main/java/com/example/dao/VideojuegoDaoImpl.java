package com.example.dao;

import com.example.database.ConexionBD;
import com.example.model.Videojuego;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideojuegoDaoImpl implements IDao<Videojuego, Integer> {

    public VideojuegoDaoImpl() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS videojuegos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    plataforma TEXT NOT NULL,
                    precio REAL NOT NULL CHECK(precio >= 0),
                    stock INTEGER NOT NULL CHECK(stock >= 0)
                )
                """;

        try (Statement st = ConexionBD.getConnection().createStatement()) {
            st.execute(sql);
        }
    }

    private Videojuego map(ResultSet rs) throws SQLException {
        return new Videojuego(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("plataforma"),
                rs.getDouble("precio"),
                rs.getInt("stock")
        );
    }

    @Override
    public List<Videojuego> findAll() throws SQLException {

        List<Videojuego> lista = new ArrayList<>();

        String sql = "SELECT * FROM videojuegos ORDER BY id";

        try (Statement st = ConexionBD.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(map(rs));
            }
        }

        return lista;
    }

    @Override
    public Optional<Videojuego> findById(Integer id) throws SQLException {

        String sql = "SELECT * FROM videojuegos WHERE id = ?";

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
    public boolean insert(Videojuego v) throws SQLException {

        String sql = """
                INSERT INTO videojuegos(nombre, plataforma, precio, stock)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps =
                     ConexionBD.getConnection().prepareStatement(sql)) {

            ps.setString(1, v.getNombre());
            ps.setString(2, v.getPlataforma());
            ps.setDouble(3, v.getPrecio());
            ps.setInt(4, v.getStock());

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean update(Videojuego v) throws SQLException {

        String sql = """
                UPDATE videojuegos
                SET nombre = ?, plataforma = ?, precio = ?, stock = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps =
                     ConexionBD.getConnection().prepareStatement(sql)) {

            ps.setString(1, v.getNombre());
            ps.setString(2, v.getPlataforma());
            ps.setDouble(3, v.getPrecio());
            ps.setInt(4, v.getStock());
            ps.setInt(5, v.getId());

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {

        String sql = "DELETE FROM videojuegos WHERE id = ?";

        try (PreparedStatement ps =
                     ConexionBD.getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() == 1;
        }
    }
}
