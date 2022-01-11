package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SystemReportsController {
    public AnchorPane systemReportPageContext;

    public void systemReportsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/IncomeReportsManagementPage.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)systemReportPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void mostMovableItemOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/MostMovableItemForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void leastMovableItemOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/LeastMovableItemForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void customerWiseIncomeOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/CustomerWiseIncomePageForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void backToAdminPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)systemReportPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
