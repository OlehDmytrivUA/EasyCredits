package com.example.creditsfx.Controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class MyCreditsWindowController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private User user = new User();

    ResultSet usercredits;

    @FXML
    private TextField CreditIDField;


    @FXML
    private Label MyCreditsLabel;


    @FXML
    private TextField TermIncCreditLineField;

    @FXML
    private Label message;

    public void setUser(User userin) throws SQLException, ClassNotFoundException {
        user.setUserid(userin.getUserid());
        user.setFirstname(userin.getFirstname());
        user.setLastname(userin.getLastname());
        user.setCreditCard(userin.getCreditCard());
        user.setUsername(userin.getUsername());
        user.setPassword(userin.getPassword());
        DBHandler dbHandler = new DBHandler();
        usercredits = dbHandler.GetUserCreditByUserID(user.getUserid());
        PrintCredits(usercredits);
    }

    public void Back(ActionEvent event) throws IOException {
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

    public void MonhtlyPayment(ActionEvent event) throws SQLException, ClassNotFoundException {
        int ID;
        DBHandler dbHandler = new DBHandler();
        if(!CreditIDField.getText().equals("")){
            try {
                ID = Integer.parseInt(CreditIDField.getText());
            }catch (NumberFormatException e){
                message.setText("Incorect ID");
                return;
            }
        }else {
            message.setText("Empty ID");
            return;
        }
        usercredits = dbHandler.GetUserCreditByUserID(user.getUserid());
        while(usercredits.next()){
            if(ID==usercredits.getInt(1)){
                if(usercredits.getInt(5)==usercredits.getInt(8)+1){
                    dbHandler.DeleteUserCreditByID(ID);
                }
                dbHandler.MonthlyPaymentByUserCreditID(ID);
                usercredits = dbHandler.GetUserCreditByUserID(user.getUserid());
                PrintCredits(usercredits);
               break;
            }
        }
    }

    public void PrePayment(ActionEvent event) throws SQLException, ClassNotFoundException {
        int ID;
        DBHandler dbHandler = new DBHandler();
        if(!CreditIDField.getText().equals("")){
            try {
                ID = Integer.parseInt(CreditIDField.getText());
            }catch (NumberFormatException e){
                message.setText("Incorect ID");
                return;
            }
        }else {
            message.setText("Empty ID");
            return;
        }
        usercredits = dbHandler.GetUserCreditByUserID(user.getUserid());
        while(usercredits.next()){
            if(ID==usercredits.getInt(1)){
                dbHandler.DeleteUserCreditByID(ID);
                usercredits = dbHandler.GetUserCreditByUserID(user.getUserid());
                PrintCredits(usercredits);
                break;
            }
        }
    }

    public void IncreaseCreditLine(ActionEvent event) throws SQLException, ClassNotFoundException {
        int ID;
        DBHandler dbHandler = new DBHandler();
        if(!CreditIDField.getText().equals("")){
            try {
                ID = Integer.parseInt(CreditIDField.getText());
            }catch (NumberFormatException e){
                message.setText("Incorect ID");
                return;
            }
        }else {
            message.setText("Empty ID");
            return;
        }
        int Term;
        if(!TermIncCreditLineField.getText().equals("")){
            try {
                Term = Integer.parseInt(TermIncCreditLineField.getText());
            }catch (NumberFormatException e){
                message.setText("Incorect Term Parametr");
                return;
            }
        }else {
            message.setText("Empty Term Parametr");
            return;
        }
        if(Term<=0){
            message.setText("Incorect Term Parametr");
            return;
        }
        usercredits = dbHandler.GetUserCreditByUserID(user.getUserid());
        while(usercredits.next()){
            if(ID==usercredits.getInt(1)){
                dbHandler.IncreaseCreditLineByID(ID,Term);
                usercredits = dbHandler.GetUserCreditByUserID(user.getUserid());
                PrintCredits(usercredits);
                break;
            }
        }
    }

    private void PrintCredits(ResultSet usercredits) throws SQLException {
        String ResString = "";
        if(usercredits.next()) {
            do {
                ResString += "ID - " + usercredits.getInt(1) + "  Сума - " + usercredits.getInt(4) + "грн " +
                        "| Термін - " + usercredits.getInt(5) + " міс \n\t Міс.платіж - " + usercredits.getDouble(6) +
                        "| Переплата - " + usercredits.getDouble(7) +  " | Місяців сплачено - " +
                        usercredits.getInt(8) + "/" + usercredits.getInt(5) + "\n";
            }while(usercredits.next());
            MyCreditsLabel.setText(ResString);
        }else{
            MyCreditsLabel.setText("You have no credits\nyou can search some and add");
        }
    }
}