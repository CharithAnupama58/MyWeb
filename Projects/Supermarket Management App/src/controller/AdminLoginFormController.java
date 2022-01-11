package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AdminLoginFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public AnchorPane adminLoginPageContext;

    public void openAdminPageForm(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals("Admin")&txtPassword.getText().equals("1234")) {
            URL resource = getClass().getResource("../view/AdminPageForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) adminLoginPageContext.getScene().getWindow();
            window.setScene(new Scene(load));
        }else {
            new Alert(Alert.AlertType.WARNING, "Invalid Entry", ButtonType.CLOSE).show();
        }
    }

    public void backToFirstPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/FirstPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) adminLoginPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
