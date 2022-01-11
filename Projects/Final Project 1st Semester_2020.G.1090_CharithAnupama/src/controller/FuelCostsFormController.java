package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Customer;
import model.Employee;
import model.Fuel;
import model.Vehicle;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class FuelCostsFormController {
    public JFXTextField txtDate;
    public JFXTextField txtTime;
    public JFXTextField txtFuelID;
    public JFXComboBox cmbEmpID;
    public JFXTextField txtEmpName;
    public JFXTextField txtVehicleID;
    public JFXTextField txtFuelCosts;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern fuelCostsPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtFuelCosts, fuelCostsPattern);
    }

    public void initialize(){
        validateInit();
        loadDateAndTime();
        setFuelID();

        try {
            loadEmployeeIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbEmpID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setEmployeeData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setEmployeeData(String id) throws SQLException, ClassNotFoundException {
        Employee e1=new EmployeeController().getEmployee(id);
        Vehicle v1=new VehicleController().getVehicleDetails(id);
        if (e1==null|v1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else{
            txtEmpName.setText(e1.getName());
            txtVehicleID.setText(v1.getVehicleId());
        }
    }

    private void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        List<String> employeeIds=new EmployeeController().getEmployeeIds();
        cmbEmpID.getItems().addAll(employeeIds);
    }

    private void setFuelID() {
        try {
            txtFuelID.setText(new VehicleController().getFuelID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            txtTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void saveCostsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Fuel f1=new Fuel(txtFuelID.getText(),txtDate.getText(),Double.parseDouble(txtFuelCosts.getText()),txtVehicleID.getText());
        new VehicleController().saveFuelRecord(f1);
        new Alert(Alert.AlertType.CONFIRMATION,"Record Saved.").show();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean){
            btnSave.setDisable(false);
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }
        }
    }
    private Object validate() {
        for (JFXTextField textField : allValidations.keySet()) {
            Pattern pattern = allValidations.get(textField);
            if (!pattern.matcher(textField.getText()).matches()) {
                /*if pattern does not match*/
                addErrorToTheBorder(textField);
                return textField;
            }
            removeError(textField);
        }
        return true;
    }

    private void removeError(JFXTextField textField) {
        textField.setStyle("-fx-border-color: green");
    }

    private void addErrorToTheBorder(JFXTextField textField) {
        textField.setStyle("-fx-border-color: red");
        btnSave.setDisable(true);
    }
}
