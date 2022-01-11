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

public class VehicleManagementPageFormController {
    public AnchorPane vehiclePageContext;
    public AnchorPane formContext;
    public AnchorPane tblContext;

    public void initialize() throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/saveVehicleForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openSaveForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/saveVehicleForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openRemoveForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/removeVehicleForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openViewAllVehiclesForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/viewAllVehiclesForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void backToAdminPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/adminPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) vehiclePageContext.getScene().getWindow();
        window.setScene(new Scene(load));
        new FadeIn(formContext).play();
    }
}
