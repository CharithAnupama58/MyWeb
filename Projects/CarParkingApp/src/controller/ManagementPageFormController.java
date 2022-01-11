package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ManagementPageFormController {
    public ComboBox cmbParkingSystem;
    public AnchorPane managementPageContext;
    public AnchorPane tableAnchorPaneContext;

    public void initialize() {
        cmbParkingSystem.getItems().addAll("None","In Parking","On Delivery");
        cmbParkingSystem.getSelectionModel().selectFirst();
        cmbParkingSystem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.equals("In Parking")){
                FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/InParkingTableForm.fxml"));
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InParkingTableFormController controller=(InParkingTableFormController)loader.getController();
                controller.loadInParkingData();
                tableAnchorPaneContext.getScene().getWindow().hide();
                Scene scene=new Scene(parent);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();

            }else if (newValue.equals("On Delivery")){
                FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/OnDeliveryTableForm.fxml"));
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                OnDeliveryTableFormController controller=(OnDeliveryTableFormController)loader.getController();
                controller.loadData();
                tableAnchorPaneContext.getScene().getWindow().hide();
                Scene scene=new Scene(parent);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();

            }
        });
}

    public void openAddVehiclePage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddVehiclePageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)managementPageContext.getScene().getWindow();
        window.setScene(new Scene(load));

    }

    public void openAddDriverPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddDriverPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)managementPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
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
            Stage window = (Stage)managementPageContext.getScene().getWindow();
            window.setScene(new Scene(load));

        }else{
        }

    }

    public void openTablePage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/TablePageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)managementPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openTablesOnAction(ActionEvent actionEvent) {
    }
}
