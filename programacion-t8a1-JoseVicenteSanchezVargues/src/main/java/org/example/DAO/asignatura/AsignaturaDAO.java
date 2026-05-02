package org.example.DAO.asignatura;

import org.example.DAO.IOperationsCRUD;
import org.example.modelo.Asignatura;
import org.example.modelo.Curso;

import java.util.List;

public interface AsignaturaDAO extends IOperationsCRUD<Asignatura> {
    public List<Asignatura> getAllByCurso(Curso curso);
}
