package main.java.com.example.dao;


import com.example.database.ConexionBD;
import com.academia.model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDao implements IDao<Curso, Integer> {
    private final Connection connection = ConexionBD.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO curso(codigo, nombre) VALUES (?,?)";
    private static final String SELECT = "SELECT id, codigo, nombre FROM curso WHERE id=?";
    private static final String SELECT_ALL = "SELECT id, codigo, nombre FROM curso";
    private static final String UPDATE = "UPDATE curso SET codigo=?, nombre=? WHERE id=?";
    private static final String DELETE = "DELETE FROM curso WHERE id=?";

    @Override
    public boolean createRecord(Curso c) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, c.getCodigo());
            ps.setString(2, c.getNombre());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Curso readRecord(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Curso(rs.getInt("id"), rs.getString("codigo"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateRecord(Curso c, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, c.getCodigo());
            ps.setString(2, c.getNombre());
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
    public List<Curso> readRecords() {
        List<Curso> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(new Curso(rs.getInt("id"), rs.getString("codigo"), rs.getString("nombre")));
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}
