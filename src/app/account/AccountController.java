package app.account;


import app.db.DB;
import app.helpers.ControllerUtils;
import app.helpers.UserHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AccountController {
    List<Map<String, Object>> accountList = null;
    private ObservableList<ModelTable> transactions = null;
    List<Map<String, Object>> transactionsMap = null;
    ObservableList<String> types = null;
    @FXML
    ComboBox combobox;
    @FXML
    TableView<ModelTable> transactionTable;
    @FXML
    TableColumn<ModelTable, String> col_sender;
    @FXML
    TableColumn<ModelTable, String> col_message;
    @FXML
    TableColumn<ModelTable, Double> col_amount;
    @FXML
    TableColumn<ModelTable, String> col_receiver;
    @FXML
    TableColumn<ModelTable, String> col_time;
    @FXML
    TextField accountName;
    @FXML
    ComboBox<String> typeOfAccount;
    @FXML
    Pane navbar;
    @FXML
    Label accountMessage;
    private String selectedAccount;

    @FXML
    public void initialize() throws IOException {
        ControllerUtils.loadFxmlFile(navbar);
        setAccountChooser();
        selectAccountType();
        System.out.println("initialize account");
        col_sender.setCellValueFactory(new PropertyValueFactory<>("senderaccount"));
        col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_receiver.setCellValueFactory(new PropertyValueFactory<>("receiveraccount"));

    }

    //Display right accounts in comboBox
    public void setAccountChooser() {
        accountList = DB.getAccountForUser();
        List<String> items = accountList.stream().map(map -> map.get("account_name").toString()).collect(Collectors.toList());
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        items
                );
        combobox.setItems(options);
    }

    //Creating new account and inserting in DB
    public void createAccount() throws SQLException {
        int selectedIndex = typeOfAccount.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            accountMessage.setText("Choose type");
            return;
        }
        String typeOfAccount = types.get(selectedIndex);
        Long accountnumber = (new Date()).getTime();
        Long user_id = UserHandler.getInstance().getUser().getId();
        Double balance = 0.0;
        String account_name = accountName.getText();

        String query = " insert into accounts (accountnumber, user_id, balance, type, account_name)"
                + " values (?, ?, ?, ?,?)";
        if (!ControllerUtils.hasAccountType(accountList, typeOfAccount) && accountName.getText().length() > 2) {
            accountMessage.setText("Account has been added");
            DB.addAccount(accountnumber, user_id, balance, typeOfAccount, account_name, query);
        } else {
            accountMessage.setText("Duplicated account type or no name");
        }
        setAccountChooser();
        accountName.clear();
    }

    public void onAccountSelected() {
        int selectedIndex = combobox.getSelectionModel().getSelectedIndex();
        selectedAccount = accountList.get(selectedIndex).get("accountnumber").toString();
        transactionsMap = DB.getTransactions(selectedAccount);
        renderTransactions(10);
    }

    //Print out transactions in tableview
    public void renderTransactions(int limit) {
        if (transactionsMap == null) {
            return;
        }
        AtomicInteger counter = new AtomicInteger();
        List<ModelTable> rows = new ArrayList<>();
        transactionsMap.forEach(map -> {
            if (counter.getAndIncrement() < limit || limit < 0) {
                Double amount = Double.valueOf(map.get("amount").toString());
                String senderAccount = map.get("senderaccount").toString();
                Double formattedAmount = selectedAccount.equals(senderAccount) ? amount * -1 : amount;
                ModelTable modelTable = new ModelTable(
                        senderAccount,
                        map.get("message").toString(),
                        map.get("time").toString(),
                        map.get("receiveraccount").toString(),
                        formattedAmount
                );
                rows.add(modelTable);
            }
        });
        transactions = FXCollections.observableArrayList(rows);
        transactionTable.setItems(transactions);
    }

    //Types of account a user can have
    public void selectAccountType() {
        types = FXCollections.observableArrayList(
                "checking",
                "salary",
                "saving"
        );
        typeOfAccount.getItems().addAll(types);
    }


    //If show more is clicked show 10 first + the rest
    public void onShowMore() {
        renderTransactions(-1);
    }

}

