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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Driver;
import model.Vehicle;
import view.tm.DriverTM;
import view.tm.VehicleTm;

import java.io.IOException;
import java.net.URL;

import static controller.AddDriverPageFormController.driverList;
import static controller.AddVehiclePageFormController.vehicleList;

public class DriverTablePageFormController {

    public AnchorPane driverTablePageContext;
    public TableView<DriverTM> tblDriver;
    public TableColumn colName;
    public TableColumn colNic;
    public TableColumn colLicenseNo;
    public TableColumn colAddress;
    public TableColumn colContact;

    public void initialize() throws IOException {
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colLicenseNo.setCellValueFactory(new PropertyValueFactory<>("drivingLicenseNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));

    }

    public void backToManagementPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/TablePageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)driverTablePageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void loadData() {
        ObservableList<DriverTM> tmObservableList= FXCollections.observableArrayList();
        for (Driver temp:driverList
        ) {
            tmObservableList.add(new DriverTM(temp.getName(),temp.getNic(),temp.getDrivingLicenseNo(),temp.getAddress(),temp.getContact()));

        }
        tblDriver.setItems(tmObservableList);
    }
}
