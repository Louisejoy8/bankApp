package app.navigation;

import app.helpers.ControllerUtils;
import app.helpers.UserHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

//class made for JavaFX to avoid duplicated code in the rest of the controller-classes

public class NavigationController {
    @FXML
    VBox navbar;

    public void goToHome() { ControllerUtils.switchScene("/app/home/home.fxml"); }

    public void goToAccount() { ControllerUtils.switchScene("/app/account/account.fxml"); }

    public void goToSettings() {
        ControllerUtils.switchScene("/app/settings/settings.fxml");
    }

    public void goToTransfer() { ControllerUtils.switchScene("/app/transaction/transaction.fxml"); }

    public void logOut() {
        UserHandler.getInstance().setUser(null);
        ControllerUtils.switchScene("/app/login/login.fxml");
    }
}
