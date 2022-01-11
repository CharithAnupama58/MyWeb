package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MakeCustomerOrderPageFormController {
    public AnchorPane makeCustomerOrderContext;

    public void addNewCustomerPage(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewCustomerPage.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void makePaymentsPage(ActionEvent actionEvent) {
    }

    public void backToCashierPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)makeCustomerOrderContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
