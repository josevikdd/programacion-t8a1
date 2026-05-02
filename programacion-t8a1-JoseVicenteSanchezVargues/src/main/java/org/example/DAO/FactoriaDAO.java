package org.example.DAO;

public class FactoriaDAO {

    private static CursoDAO cursoDAO = null;

    public static CursoDAO getCursoDAO() {
        if (cursoDAO == null) {
            cursoDAO = new CursoDAO();
        }
        return cursoDAO;
    }
}
