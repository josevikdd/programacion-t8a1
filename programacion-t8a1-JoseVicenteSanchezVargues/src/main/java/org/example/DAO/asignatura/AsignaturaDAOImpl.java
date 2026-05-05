package org.example.DAO.asignatura;

import org.example.DAO.FactoriaDAO;
import org.example.DAO.profesor.ProfesorDAOImpl;
import org.example.main.GestionEscolar;
import org.example.modelo.Asignatura;
import org.example.modelo.Curso;
import org.example.modelo.Profesor;
import org.example.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaDAOImpl implements AsignaturaDAO{
    private Connection conn = ConexionBD.getConnection();
    private static ProfesorDAOImpl  profesorDAOImpl = FactoriaDAO.getProfesorDAO();

    @Override
    public Asignatura findById(Long id) {
        String sql = "SELECT * FROM asignaturas WHERE codigo = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Asignatura asignatura = null;

            while (rs.next()){
                int codigo = rs.getInt("codigo");
                String descripcion = rs.getString("nombre");

                asignatura = new Asignatura(codigo, descripcion);
            }
            rs.close();
            ps.close();
            return asignatura;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(Asignatura object) {
        String sql = "INSERT INTO asignaturas (codigo, nombre, c_profesor, c_curso) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, object.getCodigo());
            ps.setString(2, object.getNombre());
            if(object.getProfesor() != null){
                ps.setInt(3, object.getProfesor().getCodigo());
            }
            else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setInt(4, object.getCurso().getCodigo());

            int i = ps.executeUpdate();
            ps.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Asignatura object) {
        String sql = "UPDATE asignaturas SET nombre=?, c_profesor=?, c_curso=? WHERE codigo=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, object.getNombre());
            if (object.getProfesor() != null) {
                ps.setInt(2, object.getProfesor().getCodigo());
            }
            else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setInt(3, object.getCurso().getCodigo());
            ps.setInt(4, object.getCodigo());
            int i = ps.executeUpdate();
            ps.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM asignaturas WHERE codigo=?";
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
    public List<Asignatura> getAllByCurso(Curso curso) {
        List<Asignatura> asignaturas = new ArrayList<Asignatura>();

        try {
            String sql = "SELECT * FROM asignaturas WHERE c_curso=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, curso.getCodigo());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");

                Asignatura asignatura = new Asignatura(codigo, nombre);

                int cProfesor = rs.getInt("c_profesor");
                if (!rs.wasNull()) {
                    asignatura.impartir(profesorDAOImpl.findById(Long.valueOf(cProfesor)), curso);
                }
                asignaturas.add(asignatura);
            }
            rs.close();
            ps.close();
            return asignaturas;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Asignatura> getAllByProfe(Profesor profesor) {
        List<Asignatura> asignaturas = new ArrayList<Asignatura>();

        try {
            String sql = "SELECT * FROM asignaturas WHERE c_profesor=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, profesor.getCodigo());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");

                Asignatura asignatura = new Asignatura(codigo, nombre);
                asignaturas.add(asignatura);
            }
            rs.close();
            ps.close();
            return asignaturas;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Asignatura> getAllByProfeCurso(Profesor profesor, Curso curso) {
        List<Asignatura> asignaturas = new ArrayList<Asignatura>();

        try {
            String sql = "SELECT * FROM asignaturas WHERE c_profesor=? AND c_curso=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, profesor.getCodigo());
            ps.setInt(2, curso.getCodigo());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");

                Asignatura asignatura = new Asignatura(codigo, nombre);
                asignatura.impartir(profesor, curso);
                asignaturas.add(asignatura);
            }
            rs.close();
            ps.close();
            return asignaturas;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteRelacionesById(Long id) {
        String sql = "DELETE FROM alumnos_asignaturas WHERE codigo_asignatura=?";
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
    public int deleteProfesor(Profesor profesor) {
        String sql = "UPDATE asignaturas SET c_profesor=NULL WHERE  c_profesor=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, profesor.getCodigo());
            int i = ps.executeUpdate();
            return i;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
