package app.db;

import app.Entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/** A Helper class for interacting with the Database using short-commands */
public abstract class DB {

    public static PreparedStatement prep(String SQLQuery){
        return Database.getInstance().prepareStatement(SQLQuery);
    }

    public static User getMatchingUser(String username, String password){
        User result = null;
        PreparedStatement ps = prep("SELECT * FROM users WHERE username = ? AND password = ?");
        try {
            ps.setString(1, username);
            ps.setString(2, password);
            result = (User)new ObjectMapper<>(User.class).mapOne(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }

    public static void registerUser(String name, String birthdate, String username, String password,String query ) throws SQLException {
        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = Database.getInstance().prepareStatement(query);
        preparedStmt.setString(1, name);
        preparedStmt.setString(2, birthdate);
        preparedStmt.setString(3, username);
        preparedStmt.setString(4, password);

        // execute the preparedstatement
        preparedStmt.executeUpdate();
    }

    public static void addSalery(String senderaccount, String message, double amount, String time, String receiveraccount, String query) throws SQLException {
        PreparedStatement preparedStmt = Database.getInstance().prepareStatement(query);
        preparedStmt.setString(1, senderaccount);
        preparedStmt.setString(2,message);
        preparedStmt.setDouble(3, amount);
        preparedStmt.setString(4, time);
        preparedStmt.setString(5, receiveraccount);

        preparedStmt.executeUpdate();
    }

    /*
        Example method with default parameters
    public static List<Transaction> getTransactions(int accountId){ return getTransactions(accountId, 0, 10); }
    public static List<Transaction> getTransactions(int accountId, int offset){ return getTransactions(accountId, offset, offset + 10); }
    public static List<Transaction> getTransactions(int accountId, int offset, int limit){
        List<Transaction> result = null;
        PreparedStatement ps = prep("bla bla from transactions WHERE account-id = "+accountId+" OFFSET "+offset+" LIMIT "+limit);
        try {
            result = (List<Transaction>)new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }
    */


}