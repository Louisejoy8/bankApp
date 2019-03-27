package app.settings;

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
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SettingsController {
    String selectedAccount = null;
    List<Map<String, Object>> accountList = null;
    @FXML
    Pane navbar;
    @FXML
    ComboBox<String> selectAccount;
    @FXML
    ComboBox<String> selectAccountRemove;
    @FXML
    TextField newNameField;
    @FXML
    Label errorChange;
    @FXML
    Label errorDelete;

    public void initialize() throws IOException {
        setAccountsToChange();
        ControllerUtils.loadFxmlFile(navbar);
    }


    public void setAccountsToChange() {
        accountList = DB.getAccountForUser();
        List<String> items = accountList.stream().map(map -> map.get("account_name").toString()).collect(Collectors.toList());
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        items
                );

        selectAccount.setItems(options);
        selectAccountRemove.setItems(options);
    }

    @FXML
    public void onAccountSelected() {
        try {
            int selectedIndex = selectAccount.getSelectionModel().getSelectedIndex();
            selectedAccount = accountList.get(selectedIndex).get("accountnumber").toString();
        } catch (Exception e) {

        }
    }

    //Function to change name on chosen account
    public void changeName() throws SQLException {
        if (selectAccount.getSelectionModel().getSelectedIndex() >= 0
                && newNameField.getText().length() > 2) {
            String newName = newNameField.getText();
            DB.changeNameInDB(selectedAccount, newName);
            setAccountsToChange();
            newNameField.clear();
        } else {
            errorChange.setText("Choose account, and pick a name");
        }
    }

    @FXML
    public void onAccountSelectedRemove() {
        try {
            int selectedIndex = selectAccountRemove.getSelectionModel().getSelectedIndex();
            selectedAccount = accountList.get(selectedIndex).get("accountnumber").toString();
        } catch (Exception e) {

        }
    }

    public void deleteAccount() throws SQLException {
        if (selectAccountRemove.getSelectionModel().getSelectedIndex() >= 0) {
            try {
                DB.deleteAccountFromDb(selectedAccount);
                setAccountsToChange();
            } catch (Exception e) {

            }
        } else {
            errorDelete.setText("Choose an account");
        }
    }
}
