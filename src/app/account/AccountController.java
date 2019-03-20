package app.account;


import app.db.DB;
import app.helpers.ControllerUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AccountController {

    AccountChooser accountChooser = new AccountChooser();
    @FXML
    ComboBox combobox;

    @FXML
    VBox transactionBox;

    @FXML
    private void initialize() {
        setAccountChooser();
        System.out.println("initialize account");
//        loadMoreTransactions();
    }

    void loadMoreTransactions() {
//        List<Transaction> transactions = DB.getTransactions(accountId);
//        displayTransaction(/*transactions*/);
    }

    void displayTransaction(/*List<Transaction> transactions*/) {
        // For every transaction, do the following:
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/transaction/transaction.fxml"));
            Parent fxmlInstance = loader.load();
            Scene scene = new Scene(fxmlInstance);

//            TransactionController controller = loader.getController();
//            controller.setTransaction(transaction);

            //transactionBox.getChildren().add(scene.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertSalery() throws SQLException {
        String senderaccount = "workPlace";
        String message = "Your salery";
        Double amount = 10000000.5;
        String time = new SimpleDateFormat("2019.03.20.12.12.12").format(new java.util.Date());
        String receiveraccount = "mig";
        String query = " INSERT into transactions (senderaccount, message, amount, time, receiveraccount)"
                + " values (?, ?, ?, ?, ?)";

        DB.addSalery(senderaccount, message, amount, time, receiveraccount, query);
    }

    public void getAccount() {

    }

    @FXML
    void clickLoadTransactions(Event e) {
        loadMoreTransactions();
    }

    public void goToHome() {
        ControllerUtils.switchScene("/app/home/home.fxml");

    }

    public void goToAccount() {
        ControllerUtils.switchScene("/app/account/account.fxml");

    }

    public void setAccountChooser() {

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Savings account",
                        "Card account",
                        "Checking account"
                );
        combobox.setItems(options);
    }

}
