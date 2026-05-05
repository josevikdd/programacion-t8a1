package org.example.DAO.asignatura;

import org.example.DAO.IOperationsCRUD;
import org.example.modelo.Asignatura;
import org.example.modelo.Curso;
import org.example.modelo.Profesor;

import java.util.List;

public interface AsignaturaDAO extends IOperationsCRUD<Asignatura> {
    public List<Asignatura> getAllByCurso(Curso curso);
    public List<Asignatura> getAllByProfe(Profesor profesor);
    public List<Asignatura> getAllByProfeCurso(Profesor profesor, Curso curso);
    public int deleteRelacionesById (Long id);
}
