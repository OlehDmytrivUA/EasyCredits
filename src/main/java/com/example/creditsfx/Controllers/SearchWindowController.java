    package com.example.creditsfx.Controllers;

    import DataBase.DBHandler;
    import DataBase.TablesClases.Credit;
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
    import java.sql.ResultSet;
    import java.sql.SQLException;

    public class SearchWindowController {

        private Stage stage;
        private Scene scene;
        private Parent root;

        private User user = new User();
        private Credit credit = new Credit();

        @FXML
        private TextField CreditIDtoAddField;

        @FXML
        private TextField CreditSumToAddField;

        @FXML
        private TextField CreditTermToAddField;

        @FXML
        private Label CreditsLabel;

        @FXML
        private TextField PersentField;

        @FXML
        private TextField SumField;

        @FXML
        private TextField TermField;

        @FXML
        private Label message;

        public void setUser(User userin){
            user.setUserid(userin.getUserid());
            user.setFirstname(userin.getFirstname());
            user.setLastname(userin.getLastname());
            user.setCreditCard(userin.getCreditCard());
            user.setUsername(userin.getUsername());
            user.setPassword(userin.getPassword());
        }

        public void SearchCredits(ActionEvent event) throws SQLException, ClassNotFoundException {
            DBHandler dbHandler = new DBHandler();
            int percent = -1, sum = -1, term  = -1;
            if(!PersentField.getText().equals("")){
                try {
                    percent = Integer.parseInt(PersentField.getText());
                }catch (NumberFormatException e){
                    message.setText("Incorect\nsearch\nparametr");
                    return;
                }
            }
            if(!SumField.getText().equals("")){
                try {
                    sum = Integer.parseInt(SumField.getText());
                }catch (NumberFormatException e){
                    message.setText("Incorect\nsearch\nparametr");
                    return;
                }
            }
            if(!TermField.getText().equals("")){
                try {
                    term = Integer.parseInt(TermField.getText());
                }catch (NumberFormatException e){
                    message.setText("Incorect\nsearch\nparametr");
                    return;
                }
            }
            if(percent < 0 && sum < 0 && term < 0){
                ResultSet resultSet = dbHandler.GetAllCredits();
                PrintCredits(resultSet);
                return;
            }
            ResultSet resultSet = dbHandler.GetSearchedCredits(percent,sum,term);
            PrintCredits(resultSet);
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

        public void ADDCredit(ActionEvent event) throws SQLException, ClassNotFoundException {
            ResultSet resultSet;
            DBHandler dbHandler = new DBHandler();
            int id = -1, sum = -1, term = -1;

            if(!CreditIDtoAddField.getText().equals("")){
                try {
                    id = Integer.parseInt(CreditIDtoAddField.getText());
                    System.out.println(id+"\n");
                }catch (NumberFormatException e){
                    message.setText("Incorect ID");
                    return;
                }
            }else{message.setText("Empty ID");return;}

            if(!CreditSumToAddField.getText().equals("")){
                try {
                    sum = Integer.parseInt(CreditSumToAddField.getText());
                }catch (NumberFormatException e){
                    message.setText("Incorect sum parametr");
                    return;
                }
            }else {
                message.setText("Empty Sum parametr");
                return;
            }

            if(!CreditTermToAddField.getText().equals("")){
                try {
                    term = Integer.parseInt(CreditTermToAddField.getText());
                }catch (NumberFormatException e){
                    message.setText("Incorect term parametr");
                    return;
                }
            }else {
                message.setText("Empty term parametr");
                return;
            }
            if(sum<=0||term<=0){message.setText("Sum and Term should be > 0");return;}

            resultSet = dbHandler.GetCredit(id,sum,term);

            if(resultSet.next()){
                credit.setID(resultSet.getInt(1));
                credit.setBank(resultSet.getString(2));
                credit.setCreditName(resultSet.getString(3));
                credit.setMinSum(resultSet.getInt(4));
                credit.setMaxSum(resultSet.getInt(5));
                credit.setMinTerm(resultSet.getInt(6));
                credit.setMaxTerm(resultSet.getInt(7));
                credit.setPercent(resultSet.getInt(8));
            }else{
                message.setText("There no credits with this ID\n" +
                        "or Sum and Term is incorect");
                return;
            }

            if(sum<credit.getMinSum()||sum>credit.getMaxSum()||term<credit.getMinTerm()||term>credit.getMaxTerm()){
                message.setText("Sum and Term should be\nin the interval\nof selected credit\nCheck your parametr please");
                return;
            }
            dbHandler.CreateNewUserCredit(user.getUserid(),credit.getID(),sum,term,credit.getPercent());

        }

        private void PrintCredits(ResultSet credits) throws SQLException {
            String ResString = "";
            if(credits.next()) {
                do {
                    ResString += "ID - " + credits.getString(1) + " | Банк - " + credits.getString(2)
                            + " | Назва - " + credits.getString(3) + "\n\tСума( " + credits.getString(4) + "-"
                            + credits.getString(5) + " грн) Термін( " + credits.getString(6) + "-"
                            + credits.getString(7) + " міс) Відсоток - " + credits.getString(8) + "\n";
                }while(credits.next());
                CreditsLabel.setText(ResString);
            }else{
                CreditsLabel.setText("There no such credits in database");
            }

        }

    }