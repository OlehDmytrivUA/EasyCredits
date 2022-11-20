package com.example.creditsfx.Controllers;

import DataBase.DBHandler;
import DataBase.TablesClases.User;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class StartWindowController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label Message;

    @FXML
    private TextField PasswordField;

    @FXML
    private TextField UserNameField;

    public void Login(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();
        if(UserNameField.getText().equals("") || PasswordField.getText().equals("")){
            return;
        }
        ResultSet result = dbHandler.GetUser(UserNameField.getText(), PasswordField.getText());
        User user = new User();
        if(result.next()) {
            user.setUserid(result.getInt(1));
            user.setFirstname(result.getString(2));
            user.setLastname(result.getString(3));
            user.setCreditCard(result.getString(4));
            user.setUsername(result.getString(5));
            user.setPassword(result.getString(6));
        }else{
            Message.setText("Incorect UserName or Password , try again");
            return;
        }
        System.out.println(user);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        root = loader.load();
        MainWindowController mainWindowController = loader.getController();
        mainWindowController.setUser(user);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("EasyCredits");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void ToSingUpWindow(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SingUpWindow.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("EasyCredits");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}