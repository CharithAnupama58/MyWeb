package controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class EmployeePaymentManagementController {
    public AnchorPane employeePaymentPageContext;

    public void openMakeEmployeePaymentPage(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/makeEmployeePaymentForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(employeePaymentPageContext).play();
    }

    public void openUpdateEmployeePaymentPage(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/updateEmployeePaymentForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(employeePaymentPageContext).play();
    }

    public void backToAdminPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/adminPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) employeePaymentPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
        new FadeIn(employeePaymentPageContext).play();
    }
}
