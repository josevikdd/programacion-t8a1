package org.example.DAO.asignatura;

import org.example.modelo.Asignatura;
import org.example.modelo.Curso;
import org.example.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaDAOImpl implements AsignaturaDAO{
    private Connection conn = ConexionBD.getConnection();

    @Override
    public List<Asignatura> getAll() {
        List<Asignatura> asignaturas = new ArrayList<Asignatura>();

        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM asignatura";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");

                Asignatura asignatura = new Asignatura(codigo, nombre);

                /*if (!Integer.toString(rs.getInt("c_profesor")).equals("")){ //aquigg necesito crear profesores?
                    asignatura.impartir(aqui DAO profesor llamar a profe);
                }*/

                if (!Integer.toString(rs.getInt("c_curso")).equals("")){
                    Curso curso = null;
                    curso = curso.findById(Long.valueOf(rs.getInt("c_curso")));
                    asignatura.setCurso(curso);
                }

                asignaturas.add(asignatura);
            }

            rs.close();
            st.close();
            return asignaturas;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Asignatura findById(Long id) {
        String sql = "SELECT * FROM asignatura WHERE codigo = ?";
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
        String sql = "INSERT INTO asignatura (codigo, nombre, c_profesor, c_curso) VALUES (?, ?, ?, ?)";
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
        String sql = "UPDATE curso SET nombre=?, c_profesor=?, c_curso=? WHERE codigo=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, object.getNombre());
            ps.setInt(2, object.getProfesor().getCodigo());
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
        String sql = "DELETE FROM asignatura WHERE codigo=?";
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
