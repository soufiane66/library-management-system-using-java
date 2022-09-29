package com.gama.library.controllers;


import com.gama.library.models.Book;
import com.gama.library.models.BookProperty;
import com.gama.library.services.BookImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookList implements Initializable {


    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<BookProperty> table_view;
    @FXML
    private TableColumn<BookProperty,String> id_cl;
    @FXML
    private TableColumn<BookProperty,String> title_cl;
    @FXML
    private TableColumn<BookProperty,String> author_cl;
    @FXML
    private TableColumn<BookProperty,String> publisher_cl;
    @FXML
    private TableColumn<BookProperty,Boolean> availability_cl;

    private BookImp bookService = new BookImp();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeColumns();
        loadData();

    }

    private void loadData() {
        List<Book> bookList = bookService.findAll();
        bookList.forEach(System.out::println);
        ObservableList<BookProperty> observableList = FXCollections.observableArrayList();
        for (Book book : bookList) {
            BookProperty bookProperty = new BookProperty(book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPrice(), book.isAvailable());
            observableList.add(bookProperty);
        }
        table_view.getItems().addAll(observableList);
    }


    private void initializeColumns(){
        id_cl.setCellValueFactory(new PropertyValueFactory<>("id"));
        title_cl.setCellValueFactory(new PropertyValueFactory<>("title"));
        author_cl.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisher_cl.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availability_cl.setCellValueFactory(new PropertyValueFactory<>("available"));
    }
}
