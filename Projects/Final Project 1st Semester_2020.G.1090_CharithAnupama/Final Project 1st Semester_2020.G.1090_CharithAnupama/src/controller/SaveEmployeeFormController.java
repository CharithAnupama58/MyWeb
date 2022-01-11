package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.DaySalary;
import model.Employee;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SaveEmployeeFormController {
    public JFXTextField txtEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpContact;
    public JFXTextField txtEmpNic;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtPriceForADay;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(E-00)[0-9]{1,3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern contactPattern=Pattern.compile("^[0-9]{10,15}$");
    Pattern nicPattern=Pattern.compile("^[0-9v]{10,15}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern pricePattern=Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    public void initialize(){
        validateInit();
    }
    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtEmpId, idPattern);
        allValidations.put(txtEmpName, namePattern);
        allValidations.put(txtEmpContact, contactPattern);
        allValidations.put(txtEmpNic, nicPattern);
        allValidations.put(txtEmpAddress, addressPattern);
        allValidations.put(txtPriceForADay, pricePattern);
    }

    public void saveEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Employee e1 = new Employee(
                txtEmpId.getText(), txtEmpName.getText(),
                txtEmpAddress.getText(), txtEmpContact.getText(), txtEmpNic.getText()
        );
        DaySalary e4=new DaySalary(txtEmpId.getText(),Double.parseDouble(txtPriceForADay.getText()));

        String employeeId = txtEmpId.getText();
        double salary=Double.parseDouble(txtPriceForADay.getText());
        Employee e2 = new EmployeeController().getEmployee(employeeId);
        if (e2 == null) {
            new EmployeeController().saveEmployee(e1);
            new EmployeeController().saveEmployeeSalary(employeeId,salary);
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Employee ID Already Exists.").show();
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
