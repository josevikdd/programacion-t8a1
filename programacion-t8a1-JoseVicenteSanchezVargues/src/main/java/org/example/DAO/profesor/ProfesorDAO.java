package org.example.DAO.profesor;

import org.example.DAO.IOperationsCRUD;
import org.example.modelo.Asignatura;
import org.example.modelo.Curso;
import org.example.modelo.Profesor;

import java.util.List;

public interface ProfesorDAO extends IOperationsCRUD<Profesor> {
    public List<Profesor> getAllByCurso(Curso curso);
}
