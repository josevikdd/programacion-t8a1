package org.example.DAO;

import org.example.DAO.asignatura.AsignaturaDAOImpl;
import org.example.DAO.curso.CursoDAOImpl;
import org.example.DAO.profesor.ProfesorDAOImpl;

public class FactoriaDAO {

    private static CursoDAOImpl cursoDAOImpl = null;
    private static AsignaturaDAOImpl asignaturaDAOImpl = null;
    private static ProfesorDAOImpl profesorDAOImpl = null;

    public static CursoDAOImpl getCursoDAO() {
        if (cursoDAOImpl == null) {
            cursoDAOImpl = new CursoDAOImpl();
        }
        return cursoDAOImpl;
    }

    public static AsignaturaDAOImpl getAsignaturaDAO() {
        if (asignaturaDAOImpl == null) {
            asignaturaDAOImpl = new AsignaturaDAOImpl();
        }
        return asignaturaDAOImpl;
    }

    public static ProfesorDAOImpl getProfesorDAO() {
        if (profesorDAOImpl == null) {
            profesorDAOImpl = new ProfesorDAOImpl();
        }
        return profesorDAOImpl;
    }
}
