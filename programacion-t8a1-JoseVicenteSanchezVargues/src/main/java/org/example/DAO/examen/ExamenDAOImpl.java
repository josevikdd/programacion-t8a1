package org.example.DAO.examen;

import org.example.DAO.IOperationsCRUD;
import org.example.modelo.Curso;
import org.example.modelo.Examen;
import org.example.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExamenDAOImpl implements ExamenDAO {

    private Connection conn = ConexionBD.getConnection();

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
}
