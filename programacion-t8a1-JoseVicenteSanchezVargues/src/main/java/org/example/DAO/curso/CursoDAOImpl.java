package org.example.DAO.curso;

import org.example.DAO.IOperationsCRUD;
import org.example.modelo.Curso;
import org.example.utils.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAOImpl implements CursoDAO {

    private Connection conn = ConexionBD.getConnection();

    @Override
    public List<Curso> getAll() {
        List<Curso> cursos = new ArrayList<Curso>();

        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM cursos";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("descripcion");

                Curso curso = new Curso(codigo, nombre);
                cursos.add(curso);
            }
            rs.close();
            st.close();

            return cursos;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Curso findById(Long id) {
        String sql = "SELECT * FROM cursos WHERE codigo = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Curso curso = null;

            while (rs.next()){
                int codigo = rs.getInt("codigo");
                String descripcion = rs.getString("descripcion");

                curso = new Curso(codigo, descripcion);
            }
            rs.close();
            ps.close();
            return curso;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(Curso object) {
        String sql = "INSERT INTO cursos (codigo, descripcion) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, object.getCodigo());
            ps.setString(2, object.getDescripcion());
            int i = ps.executeUpdate();
            ps.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Curso object) {
        String sql = "UPDATE cursos SET descripcion=? WHERE codigo=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, object.getDescripcion());
            ps.setInt(2, object.getCodigo());
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
        String sql = "DELETE FROM cursos WHERE codigo=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            int i = ps.executeUpdate();
            ps.close();
            return i;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int findByAlumno(Long codAlumno) {
        String sql = "SELECT * FROM alumnos WHERE codigo =?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, codAlumno);
            ResultSet rs = ps.executeQuery();

            int codigo = 0;
            while (rs.next()){
                codigo = rs.getInt("c_curso");
            }
            rs.close();
            ps.close();
            return codigo;

        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
