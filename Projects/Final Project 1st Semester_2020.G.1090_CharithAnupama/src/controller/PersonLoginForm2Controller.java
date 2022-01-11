package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PersonLoginForm2Controller {
    public AnchorPane personLoginContext;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;

    public void backToLoginPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/loginPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) personLoginContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openEmployeePage(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals("Employee")&txtPassword.getText().equals("5678")) {
            URL resource = getClass().getResource("../view/employeePageForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) personLoginContext.getScene().getWindow();
            window.setScene(new Scene(load));
        }else{
            new Alert(Alert.AlertType.WARNING,"Invalid Entry.").show();
        }
    }
}
