package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Driver;
import model.InParking;
import model.OnDelivery;
import model.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import static controller.AddDriverPageFormController.driverList;
import static controller.AddVehiclePageFormController.vehicleList;

public class FirstPageFormController {
    public AnchorPane firstPageContext;
    public ComboBox cmbVehicleNumber;
    public ComboBox cmbDriverName;
    public JFXTextField txtType;
    public JFXTextField time;
    public JFXTextField txtdate;
    public JFXButton btnOnDelivery;
    public String vehicleNumber;
    public String driverName;
    static ArrayList<OnDelivery> onDeliveryList=new ArrayList();
    public Label labSlot;
    public JFXButton btnParkVehicle;

    static ArrayList<InParking> inParkingTableList=new ArrayList();
    static String[][] parkingSlot={{"Van",null},{"Van",null},{"Van",null},{"Van",null},{"CargoLorry",null},{"CargoLorry",null},{"CargoLorry",null},{"CargoLorry",null},{"CargoLorry",null},{"CargoLorry",null},{"CargoLorry",null},{"Van",null},{"Van",null},{"Bus",null}};




    public void initialize() {
        ObservableList<String> tmVehicleObservableList = FXCollections.observableArrayList();
        for (Vehicle temp : vehicleList
        ) {
            tmVehicleObservableList.add(temp.getVehicleNum());
        }
        cmbVehicleNumber.setItems(tmVehicleObservableList);

        cmbVehicleNumber.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String type = "None";
            for (Vehicle temp1 : vehicleList
            ) {
                if (newValue.equals(temp1.getVehicleNum())) {
                    type = temp1.getVehicleType();
                }

            }
            txtType.setText(type);
            vehicleNumber = newValue.toString();

        });
        ObservableList<String> tmDriverObservableList = FXCollections.observableArrayList();
        for (Driver temp2 : driverList
        ) {
            tmDriverObservableList.add(temp2.getName());
        }
        cmbDriverName.setItems(tmDriverObservableList);
        cmbDriverName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            slot();
        });

        Thread clock = new Thread() {
            public void run() {
                for (; ; ) {
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                    Calendar cal = Calendar.getInstance();

                    int second = cal.get(Calendar.SECOND);
                    int minute = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    //System.out.println(hour + ":" + (minute) + ":" + second);
                    String state = null;
                    if (hour >= 12) {
                        state = "PM";
                    } else {
                        state = "AM";
                    }
                    time.setText("    " + String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second) + " " + state);

                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        //...
                    }
                }
            }
        };
        clock.start();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
        txtdate.setText(dateFormat.format(date));
    }

    public void parkVehicleOnAction(ActionEvent actionEvent) {
            onDeliveryList.removeIf(temp -> temp.getVehicleNumber().equals(cmbVehicleNumber.getSelectionModel().getSelectedItem().toString()));
            parkingSlot[Integer.parseInt(labSlot.getText()) - 1][1] = cmbVehicleNumber.getSelectionModel().getSelectedItem().toString();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm a");
            Date date = new Date();

            InParking park = new InParking(cmbVehicleNumber.getSelectionModel().getSelectedItem().toString(), txtType.getText(), labSlot.getText(), formatter.format(date));
            inParkingTableList.add(park);
            new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.CLOSE).show();

    }

    public void onDeliveryShiftVehicleOnAction(ActionEvent actionEvent) {
            inParkingTableList.removeIf(temp -> temp.getVehicleNumber().equals(cmbVehicleNumber.getSelectionModel().getSelectedItem().toString()));
            parkingSlot[Integer.parseInt(labSlot.getText()) - 1][1] = null;

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm a");
            Date date = new Date();

            OnDelivery delivery = new OnDelivery(cmbVehicleNumber.getSelectionModel().getSelectedItem().toString(), txtType.getText(), cmbDriverName.getSelectionModel().getSelectedItem().toString(), formatter.format(date));
            if (isExists(delivery)) {
                onDeliveryList.add(delivery);
                new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.CLOSE).show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Invalid Driver Name", ButtonType.CLOSE).show();
            }

    }

    private boolean isExists(OnDelivery delivery) {
        for (OnDelivery t:onDeliveryList
        ) {
            if (t.getDriverName().equalsIgnoreCase(delivery.getDriverName())){
                return false;
            }
        }
        return true;
    }

    public void openManagementLoginPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) firstPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void slot(){
        for (int i = 0; i <parkingSlot.length ; i++) {
            if (cmbVehicleNumber.getSelectionModel().getSelectedItem().toString().equals(parkingSlot[i][1])){
                btnParkVehicle.setDisable(true);
                btnOnDelivery.setDisable(false);
                labSlot.setText(String.format("%02d", i+1));
                return;

            }else {
               btnOnDelivery.setDisable(true);
               btnParkVehicle.setDisable(false);
                switch (txtType.getText()) {
                    case "Bus":
                        labSlot.setText("14");

                        break;
                    case "Van":
                        for (int j = 0; j < parkingSlot.length; j++) {
                            if (parkingSlot[j][0].equals("Van") && parkingSlot[j][1] == null) {
                                labSlot.setText(String.format("%02d", j + 1));
                                break;
                            }
                        }
                        break;
                    case "CargoLorry":
                        for (int k = 0; k < parkingSlot.length; k++) {
                            if (parkingSlot[k][0].equals("CargoLorry") && parkingSlot[k][1] == null) {
                                labSlot.setText(String.format("%02d", k + 1));
                                break;
                            }
                        }
                        break;
                }

            }
        }
    }
}
