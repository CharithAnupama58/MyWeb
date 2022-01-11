package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.VehicleTM;

import java.sql.SQLException;

public class ViewAllVehiclesFormController {
    public TableView<VehicleTM> tblVehicle;
    public TableColumn colVehicleId;
    public TableColumn colVehicleNumber;
    public TableColumn colEmployeeId;

    public void initialize() throws SQLException, ClassNotFoundException {

        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empID"));

        ObservableList<VehicleTM> oblist=new VehicleController().getAllVehicles();
        tblVehicle.setItems(oblist);
    }
}
