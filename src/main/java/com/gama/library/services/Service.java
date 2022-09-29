package com.gama.library.services;

import com.gama.library.entities.BookEntity;
import com.gama.library.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service<T,T2> {

    boolean save (T t);

    boolean isExists(String title);

    Optional<T> findById(int id);

    List<T> findAll();

    boolean update(T t , int id);

    boolean delete(int id);

    List<T> findAllByCondition(Predicate<T2> predicate);
}
