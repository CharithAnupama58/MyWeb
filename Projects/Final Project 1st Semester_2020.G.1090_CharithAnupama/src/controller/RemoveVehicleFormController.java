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

public class RemoveVehicleFormController {
    public JFXTextField txtVehicleNumber;
    public JFXTextField txtVehicleID;
    public JFXTextField txtEmpId;
    public JFXButton btnRemove;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern vIdPattern = Pattern.compile("^(V-00)[0-9]{1,3}$");
    Pattern numberPattern = Pattern.compile("^[A-Z]{2}[-][0-9]{4}$");
    Pattern eIdPattern = Pattern.compile("^(E-00)[0-9]{1,3}$");
    public void initialize(){
        validateInit();
    }
    private void validateInit() {
        btnRemove.setDisable(true);
        allValidations.put(txtVehicleID, vIdPattern);
        allValidations.put(txtVehicleNumber, numberPattern);
        allValidations.put(txtEmpId, eIdPattern);
    }

    public void searchVehicle(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String vehicleId = txtVehicleID.getText();

        Vehicle v1= new VehicleController().getVehicle(vehicleId);
        if (v1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(v1);
        }
    }

    private void setData(Vehicle v1) {
        txtVehicleID.setText(v1.getVehicleId());
        txtVehicleNumber.setText(v1.getVehicleNumber());
        txtEmpId.setText(v1.getEmpID());
    }

    public void removeVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new VehicleController().deleteVehicle(txtVehicleID.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean){
            btnRemove.setDisable(false);
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
        btnRemove.setDisable(true);
    }
}
