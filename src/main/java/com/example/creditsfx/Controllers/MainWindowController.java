package com.example.creditsfx.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import DataBase.TablesClases.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainWindowController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private User user = new User();

    @FXML
    private Label CreditCardLabel;

    @FXML
    private Label UserNameLabel;

    public void setUser(User userin){
        user.setUserid(userin.getUserid());
        user.setFirstname(userin.getFirstname());
        user.setLastname(userin.getLastname());
        user.setCreditCard(userin.getCreditCard());
        user.setUsername(userin.getUsername());
        user.setPassword(userin.getPassword());
        UserNameLabel.setText(userin.getUsername());
        CreditCardLabel.setText(userin.getCreditCard());
    }

    public void ToSearchWindow(ActionEvent event) throws IOException {
        System.out.println(user);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchWindow.fxml"));
        root = loader.load();
        SearchWindowController searchWindowController = loader.getController();
        searchWindowController.setUser(user);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("EasyCredits");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void ToMyCreditsWindow(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        System.out.println(user);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyCreditsWindow.fxml"));
        root = loader.load();
        MyCreditsWindowController myCreditsWindowController = loader.getController();
        myCreditsWindowController.setUser(user);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("EasyCredits");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void LogOut(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartWindow.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("EasyCredits");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
