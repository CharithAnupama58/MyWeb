package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.InParking;
import model.OnDelivery;
import model.Vehicle;
import view.tm.OnDeliveryTM;
import view.tm.VehicleTm;

import java.io.IOException;
import java.net.URL;

import static controller.AddVehiclePageFormController.vehicleList;
import static controller.FirstPageFormController.onDeliveryList;

public class OnDeliveryTableFormController {

    public TableView<OnDeliveryTM> tblOnDeliveryVehicle;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleType;
    public TableColumn colDriverName;
    public TableColumn colLeftTime;
    public AnchorPane onDeliveryContext;

    public void initialize() {
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colLeftTime.setCellValueFactory(new PropertyValueFactory<>("leftTime"));
    }
    public void loadData() {
        ObservableList<OnDeliveryTM> tmObservableList= FXCollections.observableArrayList();
        for (OnDelivery temp:onDeliveryList
        ) {
            tmObservableList.add(new OnDeliveryTM(temp.getVehicleNumber(),temp.getVehicleType(),temp.getDriverName(),temp.getLeftTime()));

        }
        tblOnDeliveryVehicle.setItems(tmObservableList);
    }

    public void backToManagementPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManagementPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) onDeliveryContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
