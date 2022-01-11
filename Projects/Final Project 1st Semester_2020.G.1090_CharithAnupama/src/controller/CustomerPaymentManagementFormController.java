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

public class CustomerPaymentManagementFormController {
    public AnchorPane customerPaymentContext;

    public void openMakeCustomerPaymentForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/makeCustomerPaymentForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        new FadeIn(customerPaymentContext).play();
    }

    public void openUpdateCustomerPaymentForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/updateCustomerPaymentForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(customerPaymentContext).play();
    }

    public void backToAdminPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/adminPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) customerPaymentContext.getScene().getWindow();
        window.setScene(new Scene(load));
        new FadeIn(customerPaymentContext).play();
    }
}
