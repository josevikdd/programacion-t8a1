package org.example.DAO;

import org.example.DAO.curso.CursoDAOImpl;

public class FactoriaDAO {

    private static CursoDAOImpl cursoDAOImpl = null;

    public static CursoDAOImpl getCursoDAO() {
        if (cursoDAOImpl == null) {
            cursoDAOImpl = new CursoDAOImpl();
        }
        return cursoDAOImpl;
    }
}
