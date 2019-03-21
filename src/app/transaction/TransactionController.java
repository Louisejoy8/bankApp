package app.transaction;


import app.db.DB;
import app.helpers.ControllerUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionController {
    String selecetedAccount = null;
    List<Map<String, Object>> accountList = null;
    @FXML
    ComboBox comboboxT;
    @FXML
    TextField receiveraccountField;
    @FXML
    TextField messageField;
    @FXML
    TextField amountField;

    public TransactionController() throws SQLException {
    }

    @FXML
    private void initialize() {
        setAccountChooser();
        System.out.println("initialize transaction");
    }
    public void goToHome() {
        ControllerUtils.switchScene("/app/home/home.fxml");

    }

    public void goToAccount() {
        ControllerUtils.switchScene("/app/account/account.fxml");

    }

    public void goToTransfer() {
        ControllerUtils.switchScene("/app/transaction/transaction.fxml");
    }

    @FXML
    public void onAccountSelected() {
        int selectedIndex = comboboxT.getSelectionModel().getSelectedIndex();
        selecetedAccount = accountList.get(selectedIndex).get("accountnumber").toString();
    }

    public void setAccountChooser() {
        accountList = DB.getAccountForUser();
        List<String> items = accountList.stream().map(map -> map.get("account_name").toString()).collect(Collectors.toList());
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        items
                );
        comboboxT.setItems(options);
    }

    public void transferMoney() {
        long millis = System.currentTimeMillis();

        Long sender = Long.valueOf(selecetedAccount);
        Long receiever = Long.valueOf(receiveraccountField.getText());
        String message = messageField.getText();
        Double amount = Double.valueOf(amountField.getText());
        Date time = new Date(millis);
        DB.getMoneyTransfer(sender, receiever, message, amount, time);
    }
}
