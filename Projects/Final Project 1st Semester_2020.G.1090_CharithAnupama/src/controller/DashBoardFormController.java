package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashBoardFormController {
    public AnchorPane dashBoardFormContext;

    public void startSystemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/loginPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) dashBoardFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
