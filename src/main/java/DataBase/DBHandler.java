package DataBase;

import DataBase.TablesClases.User;

import java.sql.*;

public class DBHandler extends Configs{
    Connection dbconnection;

    public Connection getdbconnection()throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbconnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbconnection;
    }

    public int SingUpUser(User user) throws SQLException, ClassNotFoundException {
        String Insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USER_FIRSTNAME + "," + Const.USER_LASTNAME + "," + Const.USER_CREDITCARD + "," +
                Const.USER_USERNAME + "," + Const.USER_PASSWORD + ")" +
                "VALUES(?,?,?,?,?)";

        PreparedStatement prst = getdbconnection().prepareStatement(Insert);
        prst.setString(1, user.getFirstname());
        prst.setString(2, user.getLastname());
        prst.setString(3, user.getCreditCard());
        prst.setString(4, user.getUsername());
        prst.setString(5, user.getPassword());

        try {
            prst.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            return 0;
        }
        return 1;
    }

    public ResultSet GetUser(String username , String password) throws SQLException, ClassNotFoundException {
        ResultSet result;

        String select = "SELECT * FROM " + Const.USER_TABLE +
                " WHERE " + Const.USER_USERNAME + "='"+ username + "' AND " + Const.USER_PASSWORD + "='"+password+"'";

        Statement prst = getdbconnection().createStatement();

        result = prst.executeQuery(select);
        return result;
    }

    public ResultSet GetCredit(int ID, int sum, int term) throws SQLException, ClassNotFoundException {
        ResultSet result;
        Statement prst = getdbconnection().createStatement();
        String select = "SELECT * FROM " + Const.CREDITS_TABLE + " WHERE " + Const.CREDITS_id + " = " + ID +
                " AND " + Const.CREDITS_MINSUM + "<= " + sum +
                " AND " + Const.CREDITS_MAXSUM + ">=" + sum +
                " AND " + Const.CREDITS_MINTERM + "<=" + term +
                " AND " + Const.CREDITS_MAXTERM + ">=" + term;
        result = prst.executeQuery(select);
        return result;
    }

    public ResultSet GetSearchedCredits(int percent, int sum , int term) throws SQLException, ClassNotFoundException {
        ResultSet result;
        Statement prst = getdbconnection().createStatement();
        String select = "SELECT * FROM " + Const.CREDITS_TABLE + " WHERE ";
        if(percent >= 0) {
            select += Const.CREDITS_PERCENT + "<=" + percent;
            if (sum > 0) {
                select += " AND " + Const.CREDITS_MINSUM + "<= " + sum +
                        " AND " + Const.CREDITS_MAXSUM + ">=" + sum;
                if (term > 0) {
                    select += " AND " + Const.CREDITS_MINTERM + "<=" + term +
                            " AND " + Const.CREDITS_MAXTERM + ">=" + term;
                }
            }
            result = prst.executeQuery(select);
            return result;
        }
        if(sum>0){
            select += Const.CREDITS_MINSUM + "<= "+sum+
                    " AND " + Const.CREDITS_MAXSUM + ">=" + sum;
            if(term>0){
                select += " AND " + Const.CREDITS_MINTERM + "<=" + term +
                        " AND " + Const.CREDITS_MAXTERM + ">=" + term;
            }
            result = prst.executeQuery(select);
            return result;
        }
        select += Const.CREDITS_MINTERM + "<=" + term +
                " AND " + Const.CREDITS_MAXTERM + ">=" + term;
        result = prst.executeQuery(select);
        return result;
    }

    public int CreateNewUserCredit(int UserID, int CreditID, int Sum, int Term, int percent) throws SQLException, ClassNotFoundException {
        int result;
        double overpayment = Math.round(((double)Sum*((double)percent/100.0)*((double)Term/12))*100.0)/100.0;
        double monthlypayment = Math.round(((Sum+overpayment)/Term)*100.0)/100.0;
        System.out.println(UserID + " " + CreditID + " " + Sum + " " + Term + " " + percent + " " + overpayment + " " + monthlypayment);
        String Insert = "INSERT INTO " + Const.USERCREDIT_TABLE + "("+ Const.USERCREDIT_IDUSER + "," +
                Const.USERCREDIT_IDCREDIT + "," + Const.USERCREDIT_SUM + "," + Const.USERCREDIT_TERM + "," +
                Const.USERCREDIT_MONTHLYPAYMENT + "," + Const.USERCREDIT_OVERPAYMENT+ "," + Const.USERCREDIT_MONTHSPAID + ")" +
                "VALUES(?,?,?,?,?,?,?)";
        PreparedStatement prst = getdbconnection().prepareStatement(Insert);
        prst.setInt(1,UserID);
        prst.setInt(2,CreditID);
        prst.setInt(3,Sum);
        prst.setInt(4,Term);
        prst.setDouble(5,monthlypayment);
        prst.setDouble(6,overpayment);
        prst.setInt(7,0);

        try {
            prst.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            return 0;
        }
        return 1;
    }

    public ResultSet GetUserCreditByUserID(int ID) throws SQLException, ClassNotFoundException {
        ResultSet result;
        Statement prst = getdbconnection().createStatement();
        String select = "SELECT * FROM " + Const.USERCREDIT_TABLE + " WHERE " + Const.USERCREDIT_IDUSER + " = " + ID;
        result = prst.executeQuery(select);
        return result;
    }

    public void MonthlyPaymentByUserCreditID(int ID) throws SQLException, ClassNotFoundException {
        Statement prst = getdbconnection().createStatement();
        String Update = "UPDATE " + Const.USERCREDIT_TABLE + " SET\n" + Const.USERCREDIT_MONTHSPAID + " = " +
                Const.USERCREDIT_MONTHSPAID + "+1\n" + "WHERE " + Const.USERCREDIT_ID + "=" + ID;
        prst.executeUpdate(Update);
    }

    public void DeleteUserCreditByID(int ID) throws SQLException, ClassNotFoundException {
        Statement prst = getdbconnection().createStatement();
        String delete = "DELETE FROM " + Const.USERCREDIT_TABLE  + " WHERE " + Const.USERCREDIT_ID + "=" + ID;
        prst.execute(delete);
    }

    public ResultSet GetAllCredits() throws SQLException, ClassNotFoundException {
        ResultSet result;
        Statement prst = getdbconnection().createStatement();
        String select = "SELECT * FROM " + Const.CREDITS_TABLE;
        result = prst.executeQuery(select);
        return result;
    }

    public void IncreaseCreditLineByID(int ID, int term) throws SQLException, ClassNotFoundException {
        ResultSet result;
        Statement prst = getdbconnection().createStatement();
        String select = "SELECT * FROM " + Const.USERCREDIT_TABLE + " WHERE " + Const.USERCREDIT_ID + " = " + ID;
        result = prst.executeQuery(select);
        if(result.next()) {
            int IDCredit = result.getInt(3);
            int sum = result.getInt(4);
            int Term = result.getInt(5);
            int MonthsPaid = result.getInt(8);
            double MonthlyPayment = result.getDouble(6);
            result = GetCredit(IDCredit,sum,Term);
            result.next();
            Term=(Term-MonthsPaid)+term;
            int percent = result.getInt(8);
            sum -= MonthsPaid*MonthlyPayment;
            double overpayment = Math.round(((double)sum*((double)percent/100.0)*((double)Term/12))*100.0)/100.0;
            double monthlypayment = Math.round(((sum+overpayment)/Term)*100.0)/100.0;
            String Update = "UPDATE " + Const.USERCREDIT_TABLE + " SET\n"
                    + Const.USERCREDIT_TERM + "=" + Term + " , "
                    + Const.USERCREDIT_MONTHLYPAYMENT + "=" + monthlypayment + " , "
                    + Const.USERCREDIT_SUM + "=" + sum + " , "
                    + Const.USERCREDIT_OVERPAYMENT + "=" + overpayment + " , "
                    + Const.USERCREDIT_MONTHSPAID + "=0\n" +
                    " WHERE " + Const.USERCREDIT_ID + "=" + ID;
            prst.executeUpdate(Update);
        }
    }

}
