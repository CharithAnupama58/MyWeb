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
import view.tm.InParkingTM;
import view.tm.OnDeliveryTM;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static controller.FirstPageFormController.*;

public class InParkingTableFormController {
    public AnchorPane inParkingContext;
    public TableView<InParkingTM> tblInParkingVehicle;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleType;
    public TableColumn colParkingSlot;
    public TableColumn colParkedTime;

    public void initialize() {
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colParkingSlot.setCellValueFactory(new PropertyValueFactory<>("parkingSlot"));
        colParkedTime.setCellValueFactory(new PropertyValueFactory<>("parkedTime"));
    }

    public void loadInParkingData() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        ObservableList<InParkingTM> tmObservableList= FXCollections.observableArrayList();
        for (InParking temp:inParkingTableList
        ) {
            tmObservableList.add(new InParkingTM(temp.getVehicleNumber(),temp.getVehicleType(),temp.getParkingSlot(),formatter.format(date)));

        }
        tblInParkingVehicle.setItems(tmObservableList);
    }

    public void backToManagementPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManagementPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)inParkingContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
