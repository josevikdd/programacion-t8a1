package org.example.DAO.alumno;

import org.example.modelo.Alumno;
import org.example.modelo.Asignatura;
import org.example.modelo.Curso;
import org.example.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO {

    private Connection conn = ConexionBD.getConnection();

    @Override
    public Alumno findById(Long id) {
        String sql = "SELECT * FROM alumnos WHERE codigo = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Alumno alumno = null;

            if (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String poblacion = rs.getString("poblacion");
                LocalDate fNacimiento = LocalDate.parse(rs.getString("f_nacimiento"));

                alumno = new Alumno(codigo, nombre, apellidos, poblacion, fNacimiento);
            }

            rs.close();
            ps.close();
            return alumno;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(Alumno object) {
        return 0;
    }

    @Override
    public int add(Alumno object, int codCurso) {
        String sql = "INSERT INTO alumnos (codigo, nombre, apellidos, poblacion, f_nacimiento, c_curso) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, object.getCodigo());
            ps.setString(2, object.getNombre());
            ps.setString(3, object.getApellidos());
            ps.setString(4, object.getPoblacion());
            ps.setDate(5, java.sql.Date.valueOf(object.getFechaNacimiento()));
            ps.setInt(6, codCurso);
            int i = ps.executeUpdate();
            ps.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int addRelaciones(Alumno object, int codAsignatura) {
        String sql = "INSERT INTO alumnos_asignaturas (codigo_alumno, codigo_asignatura) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, object.getCodigo());
            ps.setInt(2, codAsignatura);
            int i = ps.executeUpdate();
            ps.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Alumno object) {
        String sql = "UPDATE alumnos SET nombre=?, apellidos=?, poblacion=?, f_nacimiento=? WHERE codigo=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(5, object.getCodigo());
            ps.setString(1, object.getNombre());
            ps.setString(2, object.getApellidos());
            ps.setString(3, object.getPoblacion());
            ps.setDate(4, java.sql.Date.valueOf(object.getFechaNacimiento()));
            int i = ps.executeUpdate();
            ps.close();
            return i;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM alumnos WHERE codigo=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            int i = ps.executeUpdate();
            return i;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Alumno> getAllByCurso(Curso curso) {
        List<Alumno> alumnos = new ArrayList<Alumno>();

        try {
            String sql = "SELECT * FROM alumnos WHERE c_curso=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, curso.getCodigo());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String poblacion = rs.getString("poblacion");
                LocalDate fNacimiento = LocalDate.parse(rs.getString("f_nacimiento"));

                Alumno alumno = new Alumno(codigo, nombre, apellidos, poblacion, fNacimiento);
                alumnos.add(alumno);
            }
            rs.close();
            ps.close();
            return alumnos;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteAsignaturas(Long codAlumno) {
        String sql = "DELETE FROM alumnos_asignaturas WHERE codigo_alumno=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, codAlumno);
            int i = ps.executeUpdate();
            ps.close();
            return i;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Alumno> getAllByAsignatura(Asignatura asignatura) {
        List<Alumno> alumnos = new ArrayList<Alumno>();

        try {
            String sql = "SELECT *\n" +
                            "FROM alumnos a\n" +
                                "INNER JOIN alumnos_asignaturas aa ON a.codigo = aa.codigo_alumno \n" +
                            "WHERE aa.codigo_asignatura = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, asignatura.getCodigo());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String poblacion = rs.getString("poblacion");
                LocalDate fNacimiento = LocalDate.parse(rs.getString("f_nacimiento"));

                Alumno alumno = new Alumno(codigo, nombre, apellidos, poblacion, fNacimiento);
                alumnos.add(alumno);
            }
            rs.close();
            ps.close();
            return alumnos;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteRelacionesById(Long id) {
        String sql = "DELETE FROM alumnos_asignaturas WHERE codigo_alumno=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            int i = ps.executeUpdate();
            return i;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
