package com.gama.library.entities;

public class MemberEntityBuilder {

    private int id;
    private String name;
    private String email;
    private String mobile;




    public MemberEntityBuilder setId(int id){
        this.id = id;
        return this;
    }

    public MemberEntityBuilder setName(String name){
        this.name = name;
        return this;
    }

    public MemberEntityBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public MemberEntityBuilder setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }





    public  MemberEntity Build(){
        return new MemberEntity(name, mobile, email);
    }
}
