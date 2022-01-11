package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class TablePageFormController {
    public AnchorPane tablePageContext;

    public void openVehicleTable(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/VehicleTablePageForm.fxml"));
        Parent parent = loader.load();
        VehicleTablePageFormController controller=(VehicleTablePageFormController)loader.getController();
       // controller.loadData();
        tablePageContext.getScene().getWindow().hide();
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openDriverTable(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/DriverTablePageForm.fxml"));
        Parent parent = loader.load();
        DriverTablePageFormController controller=(DriverTablePageFormController)loader.getController();
        controller.loadData();
        tablePageContext.getScene().getWindow().hide();
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void backToManagementPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManagementPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)tablePageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
