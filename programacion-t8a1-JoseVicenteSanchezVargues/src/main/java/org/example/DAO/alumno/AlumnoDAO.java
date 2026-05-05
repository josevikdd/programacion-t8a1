package org.example.DAO.alumno;

import org.example.DAO.IOperationsCRUD;
import org.example.modelo.Alumno;
import org.example.modelo.Asignatura;
import org.example.modelo.Curso;

import java.util.List;

public interface AlumnoDAO extends IOperationsCRUD<Alumno> {
    public int add(Alumno alumno, int codCurso);
    public List<Alumno> getAllByCurso(Curso curso);
    public int deleteAsignaturas(Long codAlumno);
    public List<Alumno> getAllByAsignatura(Asignatura asignatura);
    public int deleteRelacionesById (Long id);
}
