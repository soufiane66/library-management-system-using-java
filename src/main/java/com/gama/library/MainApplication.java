package com.gama.library;

import com.gama.library.database.LibraryDB;
import com.gama.library.util.ConnectionManger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader bookList = new FXMLLoader(MainApplication.class.getResource("book_list.fxml"));
        FXMLLoader addBook = new FXMLLoader(MainApplication.class.getResource("add_book.fxml"));
        FXMLLoader addMember = new FXMLLoader(MainApplication.class.getResource("scenes/add_member.fxml"));
        FXMLLoader memberList = new FXMLLoader(MainApplication.class.getResource("scenes/member_list.fxml"));
        FXMLLoader main_activity = new FXMLLoader(MainApplication.class.getResource("scenes/main_activity.fxml"));
            Scene scene = new Scene(main_activity.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            new Thread(() -> {
                System.out.println("initializing database.........");
                Connection connection = ConnectionManger.getConnectionManger().getConnection();
                if(connection == null){
                    System.out.println("MainApplication : there is no connection");
                }else {
                    LibraryDB libraryDB = LibraryDB.getInstance();
                    libraryDB.initializeDb();
                }


            }).start();


    }
}
