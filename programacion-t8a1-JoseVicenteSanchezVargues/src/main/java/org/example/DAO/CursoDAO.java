package org.example.DAO;

import org.example.modelo.Curso;
import org.example.utils.ConexionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO implements IOperationsCRUD<Curso>{

    private Connection conn = ConexionBD.getConnection();

    @Override
    public List<Curso> getAll() {
        List<Curso> cursos = new ArrayList<Curso>();

        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM curso";
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
        return null;
    }

    @Override
    public int add(Curso object) {
        return 0;
    }

    @Override
    public int update(Curso object) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }
}
