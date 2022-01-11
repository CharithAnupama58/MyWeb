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

public class CashierPageFormController {
    public AnchorPane cashierPageContext;
    public Label lblDate;
    public Label lblTime;
    public void initialize(){
        loadDateAndTime();
    }

    public void backToFirstPage(ActionEvent actionEvent) throws IOException {
        ButtonType yes=new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to Logout?",yes,no);
        alert.setTitle("Confirmation Alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no)==yes){
            URL resource = getClass().getResource("../view/FirstPageForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage)cashierPageContext.getScene().getWindow();
            window.setScene(new Scene(load));

        }else{
        }
    }

    public void openManageOrdersPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageOrderPage.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)cashierPageContext.getScene().getWindow();
        window.setScene(new Scene(load));

    }

    public void openMakeCustomerOrderPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakeCustomerOrderPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)cashierPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
    private void loadDateAndTime() {
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void openManageCustomerForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerManagementForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)cashierPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
