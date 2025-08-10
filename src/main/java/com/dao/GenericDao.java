package com.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    void save(T entity);
    Optional<T> getById(int id);
    Optional<List<T>> getAll();
    void update(T entity);
    void delete(int id);
}
