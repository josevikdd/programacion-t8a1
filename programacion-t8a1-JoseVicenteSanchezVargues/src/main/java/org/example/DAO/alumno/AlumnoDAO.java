package org.example.DAO.alumno;

import org.example.DAO.IOperationsCRUD;
import org.example.modelo.Alumno;

public interface AlumnoDAO extends IOperationsCRUD<Alumno> {
    public int add(Alumno alumno, int codCurso);
}
