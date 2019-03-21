package app.account;


import app.db.DB;
import app.helpers.ControllerUtils;
import app.helpers.UserHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AccountController implements Initializable {
    List<Map<String, Object>> accountList = null;
    private ObservableList<ModelTable> transactions = null;
    List<Map<String, Object>> transactionsMap = null;
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

    public AccountController() throws SQLException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAccountChooser();
        System.out.println("initialize account");
        col_sender.setCellValueFactory(new PropertyValueFactory<>("senderaccount"));
        col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_receiver.setCellValueFactory(new PropertyValueFactory<>("receiveraccount"));

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

    public void setAccountChooser() {
        accountList = DB.getAccountForUser();
        List<String> items = accountList.stream().map(map -> map.get("account_name").toString()).collect(Collectors.toList());
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        items
                );
        combobox.setItems(options);
    }

    public void createAccount() throws SQLException {
        Long accountnumber = (new Date()).getTime();
        Long user_id = UserHandler.getInstance().getUser().getId();
        Double amount = 0.0;
        String account_name = accountName.getText();

        String query = " insert into accounts (accountnumber, user_id, amount, account_name)"
                + " values (?, ?, ?, ?)";

        DB.addAccount(accountnumber, user_id, amount, account_name, query);
        setAccountChooser();
        accountName.clear();
    }

    public void onAccountSelected() {
        int selectedIndex = combobox.getSelectionModel().getSelectedIndex();
        String selecetedAccount = accountList.get(selectedIndex).get("accountnumber").toString();
        transactionsMap = DB.getTransactions(selecetedAccount);
        renderTransactions(10);
    }

    public void renderTransactions(int limit){
        AtomicInteger counter = new AtomicInteger();
        List<ModelTable> rows = new ArrayList<>();
        transactionsMap.stream().forEach(map -> {
            if (counter.getAndIncrement() < limit || limit < 0) {
                ModelTable modelTable = new ModelTable(
                        map.get("senderaccount").toString(),
                        map.get("message").toString(),
                        map.get("time").toString(),
                        map.get("receiveraccount").toString(),
                        Double.valueOf(map.get("amount").toString())
                );
                rows.add(modelTable);
            }
        });
        transactions = FXCollections.observableArrayList(rows);
        transactionTable.setItems(transactions);
    }


    public void onShowMore() {
        renderTransactions(-1);
    }
}

