package com.gama.library.persistence.daos;

import com.gama.library.entities.BookEntity;
import com.gama.library.persistence.DB_Operations;


import java.util.List;
import java.util.function.Predicate;

public interface BookDao extends DB_Operations<BookEntity,Integer> {

    List<BookEntity> findAllByCondition(Predicate<BookEntity> predicate);



}
