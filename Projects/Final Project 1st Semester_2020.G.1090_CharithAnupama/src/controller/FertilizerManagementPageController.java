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

public class FertilizerManagementPageController {
    public AnchorPane fertilizePageContext;
    public AnchorPane formContext;

    public void initialize() throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/saveFertilizerForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openAddFertilizeForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/saveFertilizerForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();

    }

    public void openRemoveFertilizerForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/removeFertilizerForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void backToAdminPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/adminPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) fertilizePageContext.getScene().getWindow();
        window.setScene(new Scene(load));
        new FadeIn(formContext).play();
    }
}
