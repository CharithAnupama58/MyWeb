package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Bus;
import model.CargoLorry;
import model.Van;
import model.Vehicle;

import javax.swing.text.Caret;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddVehiclePageFormController {
    public AnchorPane AddVehiclePageContext;
    public JFXTextField txtNumber;
    public JFXTextField txtWeight;
    public JFXTextField txtNoOfPassengers;
    public ComboBox cmbVehicleType;
    public JFXButton btnSaveVehicle;
    public String vehicleType;

    static ArrayList<Vehicle> vehicleList=new ArrayList();

    public void initialize() {
        cmbVehicleType.getItems().addAll("Bus", "Van", "CargoLorry");
        cmbVehicleType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            vehicleType=newValue.toString();

        });

    }

    public void saveVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (vehicleType.equals("Bus")){
            Bus bus=new Bus(txtNumber.getText(),vehicleType,txtWeight.getText(),txtNoOfPassengers.getText());
            if (isExists(bus)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.CLOSE).show();
                clearText();
            }else{
                new Alert(Alert.AlertType.WARNING, "Already Exists", ButtonType.CLOSE).show();
            }

        }else if (vehicleType.equals("Van")){
            Van van=new Van(txtNumber.getText(),vehicleType,txtWeight.getText(),txtNoOfPassengers.getText());
            if (isExists(van)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.CLOSE).show();
                clearText();
            }else{
                new Alert(Alert.AlertType.WARNING, "Already Exists", ButtonType.CLOSE).show();
            }

        }else if (vehicleType.equals("CargoLorry")){
            CargoLorry lorry=new CargoLorry(txtNumber.getText(),vehicleType,txtWeight.getText(),txtNoOfPassengers.getText());
            if (isExists(lorry)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.CLOSE).show();
                clearText();
            }else{
                new Alert(Alert.AlertType.WARNING, "Already Exists", ButtonType.CLOSE).show();
            }
        }

    }

    private boolean isExists(Vehicle v) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Vehicle VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,v.getVehicleNum());
        stm.setObject(2,v.getVehicleType());
        stm.setObject(3,v.getMaxWeight());
        stm.setObject(4,v.getNoOfPassengers());

        return stm.executeUpdate()>0;

    }

    public void backToManagementPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManagementPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)AddVehiclePageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
    public void clearText(){
        txtNumber.clear();
        txtWeight.clear();
        txtNoOfPassengers.clear();
    }

    public void moveToWeight(ActionEvent actionEvent) {
        txtWeight.requestFocus();
    }

    public void moveToPassengers(ActionEvent actionEvent) {
        txtNoOfPassengers.requestFocus();
    }

    public void moveToVehicleType(ActionEvent actionEvent) {
    }

    public void moveToAddVehicle(ActionEvent actionEvent) {
    }
}
