package com.gama.library.persistence.daos;

import com.gama.library.entities.BookEntity;
import com.gama.library.entities.MemberEntity;
import com.gama.library.models.Member;
import com.gama.library.persistence.DB_Operations;

import java.util.List;
import java.util.function.Predicate;

public interface MemberDao extends DB_Operations<MemberEntity,Integer> {

    List<MemberEntity> findAllByCondition(Predicate<MemberEntity> predicate);



}
