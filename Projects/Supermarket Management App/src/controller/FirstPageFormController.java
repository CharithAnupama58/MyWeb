package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class FirstPageFormController {
    public ImageView firstPageContext;

    public void openAdminLoginForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)firstPageContext .getScene().getWindow();
        window.setScene(new Scene(load));

    }

    public void openCashierLoginForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)firstPageContext .getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
