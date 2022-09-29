package com.gama.library.persistence;

import java.util.List;
import java.util.Optional;

public interface DB_Operations<T,T2> {

    boolean save (T entity);

    boolean isExistsByName(String title);

    Optional<T> findById(T2 id);


    List<T> findAll();

    boolean update(T entity , T2 id);

    boolean delete(T2 id);




}
