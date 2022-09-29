package com.gama.library.controllers;

import com.gama.library.models.Book;
import com.gama.library.models.BookProperty;
import com.gama.library.models.Member;
import com.gama.library.models.MemberProperty;
import com.gama.library.services.MemberImp;
import com.gama.library.util.ConnectionManger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class MemberList implements Initializable {

    @FXML
    private TableColumn<MemberProperty,String> email_cl;

    @FXML
    private TableColumn<MemberProperty,String> id_cl;

    @FXML
    private TableColumn<MemberProperty,String> mobile_cl;

    @FXML
    private TableColumn<MemberProperty,String> name_cl;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<MemberProperty> table_view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeColumns();
        loadData();

    }



    private void loadData() {
        MemberImp memberImp = new MemberImp();
        List<Member> memberList = memberImp.findAll();
        memberList.forEach(System.out::println);
        ObservableList<MemberProperty> observableList = FXCollections.observableArrayList();
        for (Member member : memberList) {
            MemberProperty memberProperty = new MemberProperty(member.getId(), member.getName(), member.getMobile(), member.getEmail());
            observableList.add(memberProperty);
        }
        table_view.getItems().addAll(observableList);
    }


    private void initializeColumns(){
        id_cl.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_cl.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobile_cl.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        email_cl.setCellValueFactory(new PropertyValueFactory<>("email"));

    }
}

