package org.example.DAO;

import java.util.List;

public interface IOperationsCRUD<T> {

    public List<T> getAll();
    public T findById(Long id);
    public int add(T object);
    public int update(T object);
    public int deleteById(Long id);

}
