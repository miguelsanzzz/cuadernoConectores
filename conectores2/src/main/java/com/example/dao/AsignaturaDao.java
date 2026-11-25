package main.java.com.example.dao;


import com.academia.database.ConexionBD;
import com.academia.model.Asignatura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaDao implements IDao<Asignatura, Integer> {
    private final Connection connection = ConexionBD.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO asignatura(codigo, nombre, horas_semana, id_profesor, id_curso) VALUES (?,?,?,?,?)";
    private static final String SELECT = "SELECT * FROM asignatura WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM asignatura";
    private static final String UPDATE = "UPDATE asignatura SET codigo=?, nombre=?, horas_semana=?, id_profesor=?, id_curso=? WHERE id=?";
    private static final String DELETE = "DELETE FROM asignatura WHERE id=?";

    private void rellenar(PreparedStatement ps, Asignatura a) throws SQLException {
        ps.setString(1, a.getCodigo());
        ps.setString(2, a.getNombre());
        ps.setInt(3, a.getHorasSemana());
        ps.setInt(4, a.getIdProfesor());
        ps.setInt(5, a.getIdCurso());
    }

    private Asignatura mapear(ResultSet rs) throws SQLException {
        return new Asignatura(rs.getInt("id"), rs.getString("codigo"), rs.getString("nombre"),
                rs.getInt("horas_semana"), rs.getInt("id_profesor"), rs.getInt("id_curso"));
    }

    @Override
    public boolean createRecord(Asignatura a) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            rellenar(ps, a);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Asignatura readRecord(Integer id) {
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
    public boolean updateRecord(Asignatura a, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            rellenar(ps, a);
            ps.setInt(6, id);
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
    public List<Asignatura> readRecords() {
        List<Asignatura> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}
