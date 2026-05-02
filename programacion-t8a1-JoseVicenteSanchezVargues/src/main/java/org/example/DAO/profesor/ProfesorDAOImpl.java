package org.example.DAO.profesor;

import org.example.modelo.Asignatura;
import org.example.modelo.Curso;
import org.example.modelo.Profesor;
import org.example.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAOImpl implements ProfesorDAO {

    private Connection conn = ConexionBD.getConnection();

    @Override
    public Profesor findById(Long id) {
        return null;
    }

    @Override
    public int add(Profesor object) {
        return 0;
    }

    @Override
    public int update(Profesor object) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public List<Profesor> getAllByCurso(Curso curso) {
        List<Profesor> profesores = new ArrayList<Profesor>();

        try {
            String sql = "SELECT * FROM profesores WHERE c_curso=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, curso.getCodigo());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String poblacion = rs.getString("poblacion");
                LocalDate fNacimiento = LocalDate.parse(rs.getString("f_nacimiento"));
                String telefono = rs.getString("telefono");
                String categoria = rs.getString("categoria");

                Profesor profesor = new Profesor(codigo, nombre, apellidos, poblacion, fNacimiento, telefono, categoria);
                profesores.add(profesor);
            }
            rs.close();
            ps.close();
            return profesores;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
