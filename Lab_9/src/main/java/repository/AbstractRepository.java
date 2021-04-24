package repository;

import java.util.List;

public interface AbstractRepository<T> {
    void save(T t);
    T findById(int id);
    List<T> findByName(String name);
}
