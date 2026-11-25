package com.example.dao;

import java.util.List;
import java.util.Optional;

public interface IDao<T, V> {

    List<T> findAll() throws Exception;

    Optional<T> findById(V id) throws Exception;

    boolean insert(T entity) throws Exception;

    boolean update(T entity) throws Exception;

    boolean delete(V id) throws Exception;
}
