package app.helpers;

import app.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ControllerUtils {
    public static void switchScene(String pathname) {
        ControllerUtils controllerUtils = new ControllerUtils();
        try {
            Parent bla = FXMLLoader.load(controllerUtils.getClass().getResource(pathname));
            Scene scene = new Scene(bla, 620, 600);
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void loadFxmlFile(Pane pane) throws IOException {
        var x = new FXMLLoader(ControllerUtils.class.getResource("/app/navigation/navigation.fxml"));

        Parent p = x.load();
        pane.getChildren().add(p);
    }

    public static boolean hasAccountType(List<Map<String, Object>> usersAccounts, String type) {
        AtomicReference<Boolean> foundAccount = new AtomicReference<>(false);
        usersAccounts.forEach(stringObjectMap -> {
            if (type.equalsIgnoreCase(stringObjectMap.get("type").toString())) {
                foundAccount.set(true);
            }
        });
        return foundAccount.get();
    }

    public static String getAccountByType(List<Map<String, Object>> userAccounts, String type) {
        AtomicReference<String> accountnumber = new AtomicReference<>();
        userAccounts.forEach(stringObjectMap -> {
            if (type.equalsIgnoreCase(stringObjectMap.get("type").toString())) {
                accountnumber.set(stringObjectMap.get("accountnumber").toString());
            }
        });
        return accountnumber.get();
    }

    public static Boolean gotSalary(List<Map<String, Object>> transactions, String sender){
        AtomicReference<Boolean> gotPaid = new AtomicReference<>(false);
        transactions.forEach(stringObjectMap -> {
            if(sender.equalsIgnoreCase(stringObjectMap.get("senderaccount").toString())){
                gotPaid.set(true);
            }
        });
        return gotPaid.get();
    }
}
