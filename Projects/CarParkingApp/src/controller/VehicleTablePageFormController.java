package controller;

import db.DbConnection;
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
import model.Vehicle;
import view.tm.VehicleTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static controller.AddVehiclePageFormController.vehicleList;

public class VehicleTablePageFormController {
    public AnchorPane vehicleTablePageContext;
    public TableView<VehicleTm> tblVehicle;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleType;
    public TableColumn colWeight;
    public TableColumn colNoOfPassengers;





    public void initialize() throws SQLException, ClassNotFoundException {
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNum"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("maxWeight"));
        colNoOfPassengers.setCellValueFactory(new PropertyValueFactory<>("noOfPassengers"));

        loadData();
    }
    public void loadData() throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        Statement stm = con.createStatement();
        String query = "SELECT * FROM Vehicle";
        ResultSet rst = stm.executeQuery(query);
        ObservableList<VehicleTm> obList = FXCollections.observableArrayList();
        while (rst.next()) {
            obList.add(new VehicleTm(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getNString(4)
            ));
        }
        tblVehicle.setItems(obList);

    }

    public void backToTablePage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/TablePageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)vehicleTablePageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
