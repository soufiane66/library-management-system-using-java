package com.gama.library.controllers;

import com.gama.library.models.Member;
import com.gama.library.services.MemberImp;
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

public class AddMember implements Initializable {

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField email_tf;

    @FXML
    private TextField mobile_tf;

    @FXML
    private TextField name_tf;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button save_btn;

    @FXML
    void cancelAddBook(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }

    @FXML
    void saveBook(ActionEvent event) {
        
        String name = name_tf.getText();
        String mobile = mobile_tf.getText();
        String email = email_tf.getText();

        if(name.isEmpty() | mobile.isEmpty() | email.isEmpty()){
            alertDialog(Alert.AlertType.WARNING,"Add Member","Please Fill All Fields....!");
        }else{
            MemberImp memberImp = new MemberImp();
            if(memberImp.isExists(name)){
                alertDialog(Alert.AlertType.WARNING,"Add Member","This Member Already exists....!");
                return;
            }
            memberImp.save(new Member(name,mobile,email));
            alertDialog(Alert.AlertType.INFORMATION,"Add Member","Member Successfully Saved.");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void alertDialog(Alert.AlertType type,String title, String content){
        Alert alert = new Alert(type,content);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
