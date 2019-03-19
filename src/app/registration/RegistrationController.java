package app.registration;

import app.db.DB;
import app.db.Database;
import app.helpers.ControllerUtils;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationController {
    Connection conn;
    @FXML
    TextField regName;
    @FXML
    TextField regAge;
    @FXML
    TextField regUserName;
    @FXML
    PasswordField regPassword;
    @FXML
    Label errorName;
    @FXML
    Label errorAge;
    @FXML
    Label errorUserName;
    @FXML
    Label errorPassword;

    @FXML
    private void initialize() {
        errorName.setVisible(false);
        errorAge.setVisible(false);
        errorUserName.setVisible(false);
        errorPassword.setVisible(false);

    }

    boolean validateName() {
        errorName.setVisible(false);
        if (regName.getText().length() >= 2) {
            return true;
        } else {
            errorName.setVisible(true);
            return false;
        }
    }

    boolean validateAge() {
        errorAge.setVisible(false);
        if (regAge.getText().length() >= 10) {
            return true;
        } else {
            errorAge.setVisible(true);
            return false;
        }
    }

    boolean validateUserName() {
        errorUserName.setVisible(false);
        if (regUserName.getText().length() >= 2) {
            return true;
        } else {
            errorUserName.setVisible(true);
            return false;
        }
    }

    boolean validatePassword() {
        errorPassword.setVisible(false);
        if (regPassword.getText().length() >= 2) {
            return true;
        } else {
            errorPassword.setVisible(true);
            return false;
        }
    }

    @FXML
    boolean validateRegistration() {
        if (validateName() && validateAge() && validateUserName() && validatePassword()) {
            System.out.println("worked!");
            ControllerUtils.switchScene("/app/login/login.fxml");
            return true;
        }
        return false;
    }

    void createUser() throws SQLException {
        if(validateRegistration()){
            String name = regName.getText();
            String age = regAge.getText();
            String userName = regUserName.getText();
            String password = regPassword.getText();
            String query = " insert into users (name, age, user_name, password)"
                    + " values (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = Database.getInstance().prepareStatement(query);
            preparedStmt.setString (1, name);
            preparedStmt.setString (2, age);
            preparedStmt.setString(4, userName);
            preparedStmt.setString    (5, password);

            // execute the preparedstatement
            preparedStmt.execute();
        }
    }

    @FXML
    void sendToLogin() {
        ControllerUtils.switchScene("/app/login/login.fxml");
    }
}
