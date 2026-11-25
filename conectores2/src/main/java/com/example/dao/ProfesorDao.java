package main.java.com.example.dao;

import com.academia.database.ConexionBD;
import com.academia.model.Profesor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDao implements IDao<Profesor, Integer> {
    private final Connection connection = ConexionBD.getInstance().getConnection();

    private static final String INSERT = "INSERT INTO profesor(dni, nombre, apellidos, direccion, fecha_nacimiento, codigo_postal, telefono) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT = "SELECT * FROM profesor WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM profesor";
    private static final String UPDATE = "UPDATE profesor SET dni=?, nombre=?, apellidos=?, direccion=?, fecha_nacimiento=?, codigo_postal=?, telefono=? WHERE id=?";
    private static final String DELETE = "DELETE FROM profesor WHERE id=?";

    private void rellenar(PreparedStatement ps, Profesor a) throws SQLException {
        ps.setString(1, a.getDni());
        ps.setString(2, a.getNombre());
        ps.setString(3, a.getApellidos());
        ps.setString(4, a.getDireccion());
        ps.setDate(5, Date.valueOf(a.getFechaNacimiento()));
        ps.setString(6, a.getCodigoPostal());
        ps.setString(7, a.getTelefono());
    }

    private Profesor mapear(ResultSet rs) throws SQLException {
        return new Profesor(rs.getInt("id"), rs.getString("dni"), rs.getString("nombre"),
                rs.getString("apellidos"), rs.getString("direccion"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getString("codigo_postal"), rs.getString("telefono"));
    }

    @Override
    public boolean createRecord(Profesor a) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            rellenar(ps, a);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Profesor readRecord(Integer id) {
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
    public boolean updateRecord(Profesor a, Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            rellenar(ps, a);
            ps.setInt(8, id);
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
    public List<Profesor> readRecords() {
        List<Profesor> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}