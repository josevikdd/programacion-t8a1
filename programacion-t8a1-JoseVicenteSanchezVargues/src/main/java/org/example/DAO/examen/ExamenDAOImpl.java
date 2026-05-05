package org.example.DAO.examen;

import org.example.DAO.FactoriaDAO;
import org.example.DAO.IOperationsCRUD;
import org.example.DAO.alumno.AlumnoDAOImpl;
import org.example.DAO.asignatura.AsignaturaDAOImpl;
import org.example.modelo.Alumno;
import org.example.modelo.Asignatura;
import org.example.modelo.Curso;
import org.example.modelo.Examen;
import org.example.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExamenDAOImpl implements ExamenDAO {

    private Connection conn = ConexionBD.getConnection();
    private static AsignaturaDAOImpl asignaturaDAOImpl = FactoriaDAO.getAsignaturaDAO();
    private static AlumnoDAOImpl alumnoDAOImpl = FactoriaDAO.getAlumnoDAO();

    @Override
    public Examen findById(Long id) {
        String sql = "SELECT * FROM examenes WHERE codigo = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Examen examen = null;

            while (rs.next()){
                int codigo = rs.getInt("codigo");

                examen = new Examen(codigo);
            }
            rs.close();
            ps.close();
            return examen;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(Examen object) {
        String sql = "INSERT INTO examenes (codigo, fecha, nota, c_alumno, c_asignatura) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, object.getCodigo());
            ps.setDate(2, java.sql.Date.valueOf(object.getFecha()));
            ps.setFloat(3, object.getNota());
            ps.setInt(4, object.getAlumno().getCodigo());
            ps.setInt(5, object.getAsignatura().getCodigo());

            int i = ps.executeUpdate();
            ps.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Examen object) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public List<Examen> getAllByCodAlumno(int codAlumno) {
        List<Examen> examenes = new ArrayList<Examen>();

        try {
            String sql = "SELECT * FROM examenes WHERE c_alumno=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, codAlumno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                float nota = rs.getFloat("nota");
                Asignatura asignatura = asignaturaDAOImpl.findById(Long.valueOf(rs.getString("c_asignatura")));
                Alumno alumno = alumnoDAOImpl.findById(Long.valueOf(rs.getString("c_alumno")));

                Examen examen = new Examen(codigo, alumno, asignatura, fecha, nota);

                examenes.add(examen);
            }
            rs.close();
            ps.close();
            return examenes;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
