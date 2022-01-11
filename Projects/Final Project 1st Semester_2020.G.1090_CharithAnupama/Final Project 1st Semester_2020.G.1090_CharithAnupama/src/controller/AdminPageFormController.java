package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

public class AdminPageFormController {
    public Label txtDate;
    public Label txtTime;
    public AnchorPane adminPageContext;

    public void initialize(){
        loadDateAndTime();
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            txtTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void backToLoginForm(ActionEvent actionEvent) throws IOException {
        ButtonType yes=new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to Logout?",yes,no);
        alert.setTitle("Confirmation Alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no)==yes) {
            URL resource = getClass().getResource("../view/loginPageForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) adminPageContext.getScene().getWindow();
            window.setScene(new Scene(load));
        }else{
        }
    }

    public void openCustomerPaymentForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/customerPaymentManagementForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) adminPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openEmployeeForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/employeeManagementPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) adminPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openVehicleForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/vehicleManagementPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) adminPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openEmployeePaymentForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/employeePaymentManagement.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) adminPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openFertilizerPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/fertilizerManagementPage.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) adminPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openSystemReportsPageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/systemReportsManagementPage.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) adminPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
