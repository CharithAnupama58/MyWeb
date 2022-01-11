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

public class StudentRegistrationManagementFormController {
    public AnchorPane registerForm;
    public AnchorPane formContext;

    public void openStudentForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/StudentPageForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void openCourseForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../view/CourseRegistrationForm.fxml"));
        pane=fxmlLoader.load();
        formContext.getChildren().setAll(pane);
        new FadeIn(formContext).play();
    }

    public void backToHomePage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/HomePageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) registerForm.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
