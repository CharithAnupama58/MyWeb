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

public class CustomerManagementFormController {
    public AnchorPane customerManagementPageContext;
    public AnchorPane formContext;

    public void initialize() throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/saveCustomerForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openSaveCustomerForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/saveCustomerForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();

    }

    public void openUpdateCustomerForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/updateCustomerForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();

    }

    public void openRemoveCustomerForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/removeCustomerForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openCustomerAdvanceForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/advanceForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openSelectAllCustomers(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/selectAllCustomersForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void backToEmployeePage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/employeePageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) customerManagementPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
        new FadeIn(formContext).play();
    }
}
