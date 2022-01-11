package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class IncomeReportsManagementPageController {
    public AnchorPane IncomeReportsManagementContext;

    public void openMonthlyReportPage(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/MonthlyIncomeForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openAnualReportPage(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AnualIncomeForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void backToSystemReports(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SystemReportPage.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)IncomeReportsManagementContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
