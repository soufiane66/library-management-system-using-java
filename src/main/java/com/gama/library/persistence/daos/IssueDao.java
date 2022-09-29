package com.gama.library.persistence.daos;

import com.gama.library.entities.BookEntity;
import com.gama.library.entities.IssueEntity;
import com.gama.library.persistence.DB_Operations;

import java.util.List;
import java.util.function.Predicate;

public interface IssueDao extends DB_Operations<IssueEntity,Integer> {

    List<IssueEntity> findAllByCondition(Predicate<IssueEntity> predicate);



}
