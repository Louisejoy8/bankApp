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
    void validateRegistration() {
        if (validateName() && validateAge() && validateUserName() && validatePassword()) {
            System.out.println("worked!");
            try {
                createUser();
                ControllerUtils.switchScene("/app/login/login.fxml");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void createUser() throws SQLException {
        String name = regName.getText();
        String birthdate = regAge.getText();
        String username = regUserName.getText();
        String password = regPassword.getText();
        String query = " insert into users (name, birthdate, username, password)"
                + " values (?, ?, ?, ?)";

        DB.registerUser(name, birthdate, username, password, query);
    }


    @FXML
    void sendToLogin() {
        ControllerUtils.switchScene("/app/login/login.fxml");
    }
}
