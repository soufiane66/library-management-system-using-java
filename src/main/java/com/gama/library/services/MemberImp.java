package com.gama.library.services;


import com.gama.library.entities.MemberEntity;

import com.gama.library.models.Member;
import com.gama.library.persistence.daos.MemberDaoImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MemberImp implements Service<Member, MemberEntity>{

    private final MemberDaoImp daoImp = new MemberDaoImp();

    private static MemberImp instance;


    public static MemberImp getInstance() {
        if(instance == null){
            return instance = new MemberImp();
        }
        return instance;
    }

    @Override
    public boolean save(Member member) {
        MemberEntity memberEntity = MemberEntity.builder()
                .setName(member.getName())
                .setMobile(member.getMobile())
                .setEmail(member.getEmail())
                .Build();
        return daoImp.save(memberEntity);
    }

    @Override
    public boolean isExists(String title) {
        return daoImp.isExistsByName(title);
    }

    @Override
    public Optional<Member> findById(int id) {
        Optional<MemberEntity> memberEntityOptional = daoImp.findById(id);
        if(memberEntityOptional.isPresent()){
            MemberEntity memberEntity = memberEntityOptional.get();

            return Optional.of(new Member(memberEntity.getId(), memberEntity.getName(),memberEntity.getMobile(),memberEntity.getEmail()));

        }

        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        List<MemberEntity> list = daoImp.findAll();
        List<Member> members = new ArrayList<>();
        if(list != null){
            for (MemberEntity memberEntity : list) {
                members.add(new Member(memberEntity.getId(),memberEntity.getName(),memberEntity.getMobile(), memberEntity.getEmail()));
            }
            return members;
        }
        return null;
    }

    @Override
    public boolean update(Member member, int id) {
        MemberEntity memberEntity = MemberEntity.builder()
                .setName(member.getName())
                .setMobile(member.getMobile())
                .setEmail(member.getEmail())
                .Build();
        return daoImp.update(memberEntity,id);

    }

    @Override
    public boolean delete(int id) {
        return daoImp.delete(id);
    }



    @Override
    public List<Member> findAllByCondition(Predicate<MemberEntity> predicate) {
        List<MemberEntity> memberEntityList = daoImp.findAllByCondition(predicate);
        return null;
    }

}
