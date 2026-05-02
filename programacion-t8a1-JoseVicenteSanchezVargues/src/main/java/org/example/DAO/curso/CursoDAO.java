package org.example.DAO.curso;

import org.example.DAO.IOperationsCRUD;
import org.example.modelo.Curso;

import java.util.List;

public interface CursoDAO extends IOperationsCRUD<Curso> {
    public List<Curso> getAll();
}
