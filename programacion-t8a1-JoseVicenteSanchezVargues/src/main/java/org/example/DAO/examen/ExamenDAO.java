package org.example.DAO.examen;

import org.example.DAO.IOperationsCRUD;
import org.example.modelo.Examen;

import java.util.List;

public interface ExamenDAO extends IOperationsCRUD<Examen> {
    public List<Examen> getAllByCodAlumno(int codAlumno);
    public int deleteByAlumnoId(Long id);
    public int deleteByAsignaturaId(Long id);
}
