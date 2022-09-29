package com.gama.library.core;

import com.gama.library.database.LibraryDB;
import com.gama.library.models.Member;
import com.gama.library.persistence.daos.MemberDaoImp;
import com.gama.library.services.MemberImp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App  {


    public static void main(String[] args) {



//        Book book = new Book("drama","vector","publisher", 2000.0);
//        BookImp service = new BookImp();
//
//       // LibraryDB.initializeDb();
//     // service.save(book);
//       service.delete(8);
////        System.out.println(service.findById(9));
////        service.update(book,1);
//
//        service.findAll().forEach(System.out::println);

        LibraryDB libraryDB = LibraryDB.getInstance();
        libraryDB.initializeDb();
        Member member = new Member("soufine","06149554","example.com");
        MemberImp memberImp = new MemberImp();
        //memberImp.save(member);
        memberImp.findAll().forEach(System.out::println);







    }
}
