package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ManageOrderPageController {
    public AnchorPane manageOrdersPageContext;

    public void updateOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/UpdateOrderForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void removeOrdersOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/RemoveOrderForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void backToCashierPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)manageOrdersPageContext.getScene().getWindow();
        window.setScene(new Scene(load));

    }
}
