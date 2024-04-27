package dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    T insert(T data);

    Boolean delete(T data);

    Boolean update(int id, T data);

    Optional<T> getById(int id);

    List<T> getAll();
}
