package controller;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class FertilizerOrderManagementFormController {
    public JFXButton fertilizeOrderPageContext;
    public AnchorPane formContext;

    public void initialize() throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/makeFertilizerOrderForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openMakeFertilizePage(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/makeFertilizerOrderForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openUpdateFertilizerPage(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/updateFertilizerOrdersForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void backToEmployeePage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/employeePageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) fertilizeOrderPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
        new FadeIn(formContext).play();
    }

    public void openShowAllOrders(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/showAllFertilizerOrdersForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }
}
