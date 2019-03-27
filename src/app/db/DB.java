package app.db;

import app.Entities.Account;
import app.Entities.Transaction;
import app.Entities.User;
import app.helpers.UserHandler;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * A Helper class for interacting with the Database using short-commands
 */
public abstract class DB {

    public static PreparedStatement prep(String SQLQuery) {
        return Database.getInstance().prepareStatement(SQLQuery);
    }

    public static User getMatchingUser(String username, String password) {
        User result = null;
        PreparedStatement ps = prep("SELECT * FROM users WHERE username = ? AND password = ?");
        try {
            ps.setString(1, username);
            ps.setString(2, password);
            result = (User) new ObjectMapper<>(User.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // return User;
    }

    public static void registerUser(String name, String birthdate, String username, String password, String query) throws SQLException {
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
        preparedStmt.setString(2, message);
        preparedStmt.setDouble(3, amount);
        preparedStmt.setString(4, time);
        preparedStmt.setString(5, receiveraccount);

        preparedStmt.executeUpdate();
    }

    public static void addAccount(Long accountnumber, Long user_id, Double balance, String type, String account_name, String query) throws SQLException {
        PreparedStatement preparedStmt = Database.getInstance().prepareStatement(query);
        preparedStmt.setLong(1, accountnumber);
        preparedStmt.setLong(2, user_id);
        preparedStmt.setDouble(3, balance);
        preparedStmt.setString(4, type);
        preparedStmt.setString(5, account_name);

        preparedStmt.executeUpdate();
    }

    public static List<Map<String, Object>> getAccountForUser() {
        Long user_id = UserHandler.getInstance().getUser().getId();
        PreparedStatement ps = prep("SELECT * FROM accounts WHERE user_id = ?");
        try {
            ps.setLong(1, user_id);
            return new ObjectMapper<>(Account.class).resultSetToArrayList(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map<String, Object>> getTransactions(String accountNumber) {
        PreparedStatement ps = prep("SELECT * FROM transactions WHERE senderaccount = ? OR receiveraccount = ?");
        try {
            ps.setString(1, accountNumber);
            ps.setString(2, accountNumber);
            return new ObjectMapper<>(Transaction.class).resultSetToArrayList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getMoneyTransfer(String senderaccount, String receiveraccount,
                                        String message, Double amount, Date time) {
        PreparedStatement ps = prep("UPDATE accounts set balance = balance+? WHERE accountnumber =? ");
        PreparedStatement ps1 = prep("UPDATE accounts set balance = balance-? WHERE accountnumber =? ");
        PreparedStatement ps2 = prep("INSERT INTO transactions (senderaccount, message, amount, time, receiveraccount) VALUES(?, ?, ?, ?,?)");
        try {
            ps.setDouble(1, amount);
            ps.setString(2, receiveraccount);
            ps1.setDouble(1, amount);
            ps1.setString(2, senderaccount);
            ps2.setString(1, senderaccount);
            ps2.setString(2, message);
            ps2.setDouble(3, amount);
            ps2.setDate(4, time);
            ps2.setString(5, receiveraccount);

            ps.executeUpdate();
            ps1.executeUpdate();
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> getAccountAndBalance(Long userid) {
        PreparedStatement ps = prep("SELECT account_name, balance, accountnumber, type FROM accounts WHERE user_id = ?");
        try {
            ps.setLong(1, userid);
            return new ObjectMapper<>(Transaction.class).resultSetToArrayList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void changeNameInDB(String accountnumber, String newName) throws SQLException {
        PreparedStatement ps = prep("UPDATE accounts set account_name = ? WHERE accountnumber = ?");
        ps.setString(1, newName);
        ps.setString(2, accountnumber);
        ps.executeUpdate();
    }

    public static void deleteAccountFromDb(String accountnumber) throws SQLException {
        PreparedStatement ps = prep("DELETE FROM accounts WHERE accountnumber = ?");
        ps.setString(1, accountnumber);

        ps.executeUpdate();
    }

    public static Double getBalanceFromAccount(String accountnumber) throws SQLException {
        PreparedStatement ps = prep("SELECT balance FROM accounts WHERE accountnumber = ?");
        ps.setString(1,accountnumber);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return -1.0;
    }
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
