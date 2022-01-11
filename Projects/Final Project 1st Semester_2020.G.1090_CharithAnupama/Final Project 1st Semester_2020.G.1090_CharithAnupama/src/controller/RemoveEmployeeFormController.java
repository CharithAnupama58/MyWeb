package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Employee;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class RemoveEmployeeFormController {
    public JFXTextField txtEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpContact;
    public JFXTextField txtEmpNic;
    public JFXTextField txtEmpAddress;
    public JFXButton btnRemove;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(E-00)[0-9]{1,3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern contactPattern=Pattern.compile("^[0-9]{10,15}$");
    Pattern nicPattern=Pattern.compile("^[0-9v]{10,15}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    public void initialize(){
        validateInit();
    }
    private void validateInit() {
        btnRemove.setDisable(true);
        allValidations.put(txtEmpId, idPattern);
        allValidations.put(txtEmpName, namePattern);
        allValidations.put(txtEmpContact, contactPattern);
        allValidations.put(txtEmpNic, nicPattern);
        allValidations.put(txtEmpAddress, addressPattern);
    }

    public void searchEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String EmployeeId = txtEmpId.getText();

        Employee e1= new EmployeeController().getEmployee(EmployeeId);
        if (e1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(e1);
        }
    }

    private void setData(Employee e1) {
        txtEmpId.setText(e1.getEmpID());
        txtEmpName.setText(e1.getName());
        txtEmpAddress.setText(e1.getAddress());
        txtEmpContact.setText(e1.getContact());
        txtEmpNic.setText(e1.getNic());
    }

    public void removeEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new EmployeeController().deleteEmployee(txtEmpId.getText())){
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
