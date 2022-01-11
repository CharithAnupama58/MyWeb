package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginPageFormController {
    public AnchorPane loginFormContext;

    public void openEmployeeLogin(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/personLoginForm2.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
    public void openAdminLogin(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/personLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
