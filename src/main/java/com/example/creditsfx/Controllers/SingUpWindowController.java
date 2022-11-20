package com.example.creditsfx.Controllers;

import DataBase.DBHandler;
import DataBase.TablesClases.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingUpWindowController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label Messege;

    @FXML
    private TextField CreditCardField;

    @FXML
    private TextField FirstNameField;

    @FXML
    private TextField LastNameField;

    @FXML
    private TextField PasswordField;

    @FXML
    private TextField UserNameField;

    public void SingUpUserAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        DBHandler dbHandler = new DBHandler();
        Pattern CreditCard = Pattern.compile("^[0-9]{4}+-[0-9]{4}+-[0-9]{4}+-[0-9]{4}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher;

        String firstname = FirstNameField.getText().trim();
        String lastname = LastNameField.getText().trim();
        String creditcard = CreditCardField.getText().trim();
        String username = UserNameField.getText().trim();
        String password = PasswordField.getText().trim();
        matcher = CreditCard.matcher(creditcard);

        if (matcher.matches() && !firstname.equals("") && !lastname.equals("") &&
                !creditcard.equals("") && !username.equals("") && !password.equals("")) {
            User user = new User(firstname, lastname, creditcard, username, password);
            if(dbHandler.SingUpUser(user) == 0){
                Messege.setText("There is already a user with this username, please change it");
            }else {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartWindow.fxml")));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("EasyCredits");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            };
        } else {
            Messege.setText("Some field is empty or the creditcard is incorrect, please try again");
        }
    }

    public void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartWindow.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("EasyCredits");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}

