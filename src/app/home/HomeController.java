package app.home;

import app.Entities.Account;
import app.Main;
import app.db.DB;
import app.helpers.ControllerUtils;
import app.helpers.UserHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class HomeController {
    List<Map<String, Object>> overviewListMap = null;
    private ObservableList<Account> accounts = null;
    @FXML
    TableView<Account> overViewTable;
    @FXML
    TableColumn<Account, String> col_accountName;
    @FXML
    TableColumn<Account, String> col_balance;
    @FXML
    TableColumn<Account, String> col_type;
    @FXML
    TableColumn<Account, String> col_number;
    @FXML
    Label greetingName;
    @FXML
    Pane navbar;
    @FXML
    TextField itemToBuy;
    @FXML
    Label purchasedItem;

    @FXML
    void initialize() throws IOException {
        // load accounts from db using LoginController.user.getId() and display them
        ControllerUtils.loadFxmlFile(navbar);
        col_accountName.setCellValueFactory(new PropertyValueFactory<>("account_name"));
        col_balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_number.setCellValueFactory(new PropertyValueFactory<>("accountnumber"));
        greetingName.setText("Welcome back, " + UserHandler.getInstance().getUser().getName() + "!");
        displayAccounts();
        insertSalary();

    }

    //Function that prints out every account and shows type, balance, name ect.
    public void displayAccounts() {
        overviewListMap = DB.getAccountAndBalance(UserHandler.getInstance().getUser().getId());
        List<Account> rows = new ArrayList<>();
        overviewListMap.stream().forEach(map -> {
            Account accountTable = new Account(
                    map.get("account_name").toString(),
                    Long.valueOf(map.get("accountnumber").toString()),
                    Double.valueOf(map.get("balance").toString()),
                    map.get("type").toString()

            );
            rows.add(accountTable);
        });
        accounts = FXCollections.observableArrayList(rows);
        overViewTable.setItems(accounts);
    }

    //Function to fake a purchase. Have to write what item to buy and hit button(always 200kr)
    public void buyItem() throws SQLException {
        String sender = ControllerUtils.getAccountByType(overviewListMap, "checking");
        String receiever = "Card purchase";
        String message = itemToBuy.getText();

        Double amount = 200.00;
        if (amount > DB.getBalanceFromAccount(sender)) {
            purchasedItem.setText("You do not have enough money");
            return;
        }
        Date time = new Date(System.currentTimeMillis());
        try {
            if (sender != null && itemToBuy.getText().length() > 2 && itemToBuy.getText().matches("^[a-zA-Z ]*$")) {
                DB.getMoneyTransfer(sender, receiever, message, amount, time);
                displayAccounts();
                purchasedItem.setText("You just bought " + itemToBuy.getText() + "!");
                itemToBuy.clear();
            } else {
                purchasedItem.setText("Make sure you typed correctly and have a checking account");
            }

        } catch (Exception e) {
            purchasedItem.setText("Could not buy the item, sorry!");
        }
    }

    //Function: Inserts 100.000,00 to salary account every 25 in the current month first time you log in
    public void insertSalary() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String sender = "Your boss";
        String reciever = ControllerUtils.getAccountByType(overviewListMap, "salary");
        String message = "Your salary";
        Double amount = 100000.00;
        Date time = new Date(System.currentTimeMillis());
        if (day == 25 && reciever != null && !ControllerUtils.gotSalary(DB.getTransactions(ControllerUtils.getAccountByType(overviewListMap, "salary")), "Your boss")) {
            try {
                DB.getMoneyTransfer(sender, reciever, message, amount, time);
                displayAccounts();
            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
        }
    }
}

