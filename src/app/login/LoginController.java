package app.login;


import app.Entities.User;
import app.Main;
import app.account.AccountController;
import app.db.DB;
import app.helpers.ControllerUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    ControllerUtils controllerUtils;
    //AccountController accountController = new AccountController();

    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    @FXML
    Label errorMessage;

    // Use this in other Controllers to get "the currently logged in user".
    private static User user = null;

    public static User getUser() {
        return user;
    }

    @FXML
    private void initialize() {
        errorMessage.setVisible(false);
        System.out.println("initialize login");
    }

    @FXML
    void onLogin() throws SQLException {
        user = DB.getMatchingUser(userName.getText(), password.getText());
        if (user == null) {
            errorMessage.setVisible(true);
        } else {
            errorMessage.setVisible(false);
            //accountController.insertSalery();
            controllerUtils.switchScene("/app/home/home.fxml");
        }
    }


}
