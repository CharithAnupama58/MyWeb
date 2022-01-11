package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Employee;
import model.Vehicle;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SaveVehicleFormController {
    public JFXTextField txtVehicleID;
    public JFXTextField txtVehicleNumber;
    public JFXTextField txtEmpId;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern vIdPattern = Pattern.compile("^(V-00)[0-9]{1,3}$");
    Pattern numberPattern = Pattern.compile("^[A-Z]{2}[-][0-9]{4}$");
    Pattern eIdPattern = Pattern.compile("^(E-00)[0-9]{1,3}$");
    public void initialize(){
        validateInit();
    }
    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtVehicleID, vIdPattern);
        allValidations.put(txtVehicleNumber, numberPattern);
        allValidations.put(txtEmpId, eIdPattern);
    }

    public void saveVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Vehicle v1 = new Vehicle(
                txtVehicleID.getText(), txtVehicleNumber.getText(),
                txtEmpId.getText()
        );

        String vehicleId = txtVehicleID.getText();
        String employeeId=txtEmpId.getText();
        Vehicle v2 = new VehicleController().getVehicle(vehicleId);
        Vehicle v3 = new VehicleController().getVehicleDetails(employeeId);
        if (v2 == null&v3==null) {
            new VehicleController().saveVehicle(v1);
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        }else if (v3!=null){
            new Alert(Alert.AlertType.WARNING, "Employee ID Already Exists.").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Vehicle ID Already Exists.").show();
        }
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
