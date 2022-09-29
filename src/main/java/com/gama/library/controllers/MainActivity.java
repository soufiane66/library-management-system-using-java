package com.gama.library.controllers;

import com.gama.library.MainApplication;
import com.gama.library.models.Book;
import com.gama.library.models.Issue;
import com.gama.library.models.Member;
import com.gama.library.services.BookImp;
import com.gama.library.services.IssueServiceImp;
import com.gama.library.services.MemberImp;
import com.gama.library.util.ConnectionManger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainActivity implements Initializable {


    @FXML
    private TextField book_id;

    @FXML
    private TextField member_id;

    @FXML
    private TextField author_name_txt;

    @FXML
    private TextField book_name_txt;

    @FXML
    private TextField available_tf;

    @FXML
    private TextField contact_txt;

    @FXML
    private TextField member_name_txt;

    private int memberId;
    private int bookId;

    @FXML
    void addBook(ActionEvent event) {

        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            loadWindow("scenes/add_book.fxml", "Add Book");
        }catch (NullPointerException e){
            connectionAlert();
        }




    }

    @FXML
    void addMember(ActionEvent event) {

        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            loadWindow("scenes/add_member.fxml", "Add Member");
        }catch (NullPointerException e){
            connectionAlert();
        }





    }

    @FXML
    void displayBooks(ActionEvent event) {

        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            loadWindow("scenes/book_list.fxml", "Book List");
        }catch (NullPointerException e){
            connectionAlert();
        }



    }

    @FXML
    void displayMembers(ActionEvent event) {
        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            loadWindow("scenes/member_list.fxml", "Member List");
        }catch (NullPointerException e){
            connectionAlert();
        }





    }

    @FXML
    void displaySettings(ActionEvent event) {


        loadWindow("scenes/settings_activity.fxml", "Settings");


    }

    private void connectionAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        displayAlert(alert, "Error", "There is no connection with database");
    }

    private void connectionAlert(String title,String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        displayAlert(alert, title, contentText);
    }

    @FXML
    void renew(ActionEvent event) {

    }



    @FXML
    void loadBookInfo(ActionEvent event) {
        book_name_txt.setText("");
        author_name_txt.setText("");
        String id = book_id.getText();
        BookImp bookImp = BookImp.getInstance();
        Optional<Book> optionalBook = bookImp.findById(Integer.parseInt(id));
        if(optionalBook.isPresent()){
            book_name_txt.setText(optionalBook.get().getTitle());
            author_name_txt.setText(optionalBook.get().getAuthor());
            available_tf.setText(""+optionalBook.get().isAvailable());
        }else {
            connectionAlert("Find Book", "Book ID: " + id + " doesn't exists");
        }
    }

    @FXML
    void loadMemberInfo(ActionEvent event) {


        String id = member_id.getText();
        MemberImp bookImp = MemberImp.getInstance();
        Optional<Member> optionalMember = bookImp.findById(Integer.parseInt(id));
        if(optionalMember.isPresent()){
            member_name_txt.setText(optionalMember.get().getName());
            contact_txt.setText(optionalMember.get().getEmail());
        }else {
            connectionAlert("Find Member", "Member ID: " + id + " doesn't exists");
        }


    }


    void loadWindow(String location,String title){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void loadIssueOperation(ActionEvent e){
        if(member_id.getText().isEmpty() || book_id.getText().isEmpty()){
            displayAlert(new Alert(Alert.AlertType.ERROR),"Error","please fill all fields");
            return;

        }else {
             memberId = Integer.parseInt(member_id.getText());
             bookId = Integer.parseInt(book_id.getText());
        }

        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        displayAlert(alertConfirmation, "Confirmation", "Are you sure want to issue the book ?");

        Optional<ButtonType> response = alertConfirmation.showAndWait();
        if(response.isPresent() && response.get() == ButtonType.OK){
            IssueServiceImp serviceImp = IssueServiceImp.getInstance();
            BookImp bookImp = BookImp.getInstance();
            boolean isSaved = serviceImp.save(new Issue(bookId,memberId));
            boolean isUpdated = bookImp.update("available", "false",bookId);

            if(isSaved && isUpdated){

                Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                displayAlert(alertInformation, "Information", "Book issue complete");
            }else {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                displayAlert(alertError, "Error", "issue operation failed");
            }
        }
    }

    private void displayAlert(Alert alert, String Confirmation, String contentText) {
        alert.setTitle(Confirmation);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    @FXML
    private TextField book_id_tf2;



    @FXML
    void loadBookInfo2_tf(ActionEvent event) {
        if(book_id_tf2.getText().isEmpty()){
            displayAlert(new Alert(Alert.AlertType.ERROR),"Error","please fill the field");
        }else {
            int bookID = Integer.parseInt(book_id_tf2.getText());

        }
    }



}
