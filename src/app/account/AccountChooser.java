package app.account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;


public class AccountChooser {

    @FXML
    ComboBox comboBox;

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Savings account",
                    "Card account",
                    "Checking account"
            );
}
