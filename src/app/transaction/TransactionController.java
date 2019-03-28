package app.transaction;


import app.db.DB;
import app.helpers.ControllerUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionController {
    String selectedAccount = null;
    String accountToSendTo = null;
    List<Map<String, Object>> accountList = null;
    @FXML
    ComboBox comboboxT;
    @FXML
    TextField receiveraccountField;
    @FXML
    TextField messageField;
    @FXML
    TextField amountField;
    @FXML
    Pane navbar;
    @FXML
    Label succesOrNot;
    @FXML
    ComboBox<String> owwnAccounts;

    public TransactionController() {
    }

    @FXML
    private void initialize() throws IOException {
        ControllerUtils.loadFxmlFile(navbar);
        setAccountChooser();
        System.out.println("initialize transaction");
    }


    @FXML
    public void onAccountSelected() {
        clearErrorText();
        int selectedIndex = comboboxT.getSelectionModel().getSelectedIndex();
        selectedAccount = accountList.get(selectedIndex).get("accountnumber").toString();
    }

    @FXML
    public void accountSelected() {
        clearErrorText();
        int selectedIndex = owwnAccounts.getSelectionModel().getSelectedIndex();
        accountToSendTo = accountList.get(selectedIndex).get("accountnumber").toString();
    }

    private void clearErrorText() {
        succesOrNot.setText("");
    }

    public void setAccountChooser() {
        accountList = DB.getAccountForUser();
        List<String> items = accountList.stream().map(map -> map.get("account_name").toString()).collect(Collectors.toList());
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        items
                );
        comboboxT.setItems(options);
        owwnAccounts.setItems(options);
    }

    //Function to send money to own account or someone elses.
    public void transferMoney() throws SQLException {
        long millis = System.currentTimeMillis();
        String sender = selectedAccount;
        String receiever = accountToSendTo != null ? accountToSendTo : receiveraccountField.getText();
        String message = messageField.getText();
        if (receiever == null ||
                receiever.isEmpty() ||
                message.isEmpty() ||
                amountField.getText().isEmpty() ||
                sender == null ||
                sender.isEmpty()) {
            succesOrNot.setText("Please fill in all required fields");
            return;
        } else if(receiever.equals(sender)) {
            succesOrNot.setText("Can not send to same account");
            return;
        }
        if (!amountField.getText().matches("^[0-9]*$")){
            succesOrNot.setText("Have to be correct amount");
            return;
        }
        Double amount = Double.valueOf(amountField.getText());
        Date time = new Date(millis);
        if (amount > DB.getBalanceFromAccount(selectedAccount)) {
            succesOrNot.setText("You do not have enough money");
            return;
        }
        try {
            DB.getMoneyTransfer(sender, receiever, message, amount, time);
            succesOrNot.setText("Money has been send!");
        } catch (Exception e) {
            succesOrNot.setText("Money has NOT been send");
        }
    }
}
