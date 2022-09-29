package com.gama.library.controllers;

import com.gama.library.database.LibraryDB;
import com.gama.library.models.Book;
import com.gama.library.services.BookImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBook implements Initializable {


    @FXML
    TextField  title_tf, author_tf, publisher_tf;

    @FXML
    AnchorPane root;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    Button save_btn, cancel_btn;

    @FXML
    public void saveBook(ActionEvent event) {

        String book_title = title_tf.getText();
        String book_author = author_tf.getText();
        String book_publisher = publisher_tf.getText();

        if (book_title.isEmpty() || book_author.isEmpty() || book_publisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please Fill all fields");
            alert.showAndWait();
        } else {
            Book book = new Book(book_title, book_author, book_publisher, 00.,true);
            BookImp service = new BookImp();
            if(service.isExists(book_title)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Add Book");
                alert.setContentText(book_title+" Already exists");
                alert.showAndWait();
                return;
            }
            if(service.save(book)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Add Book");
                alert.setContentText("Book Saved Successfully");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Add Book");
                alert.setContentText("Failed To save  ");
                alert.showAndWait();
            }

        }

    }

    @FXML
    public void cancelAddBook(ActionEvent event) {

        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }
}
