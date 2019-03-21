package app.helpers;

import app.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ControllerUtils {
   public static void switchScene(String pathname) {
       ControllerUtils controllerUtils = new ControllerUtils();
        try {
            Parent bla = FXMLLoader.load(controllerUtils.getClass().getResource(pathname));
            Scene scene = new Scene(bla, 800, 600);
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void getAccount(){

    }
}
