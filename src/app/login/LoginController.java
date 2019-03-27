package app.login;


import app.Entities.User;
import app.Main;
import app.account.AccountController;
import app.db.DB;
import app.helpers.ControllerUtils;
import app.helpers.UserHandler;
import javafx.event.ActionEvent;
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
            UserHandler.getInstance().setUser(user);
            controllerUtils.switchScene("/app/home/home.fxml");
        }
    }


    public void goToReg() {
        ControllerUtils.switchScene("/app/registration/registration.fxml");
    }
}
