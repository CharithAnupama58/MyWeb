package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;
import model.Employee;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateCustomerFormController {
    public JFXTextField txtCustId;
    public JFXTextField txtCustContact;
    public JFXTextField txtCustName;
    public JFXTextField txtCustNic;
    public JFXTextField txtCustAddress;
    public JFXButton btnUpdate;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(C-00)[0-9]{1,3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern contactPattern=Pattern.compile("^[0-9]{10,15}$");
    Pattern nicPattern=Pattern.compile("^[0-9v]{10,15}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    public void initialize(){
        validateInit();
    }
    private void validateInit() {
        btnUpdate.setDisable(true);
        allValidations.put(txtCustId, idPattern);
        allValidations.put(txtCustName, namePattern);
        allValidations.put(txtCustContact, contactPattern);
        allValidations.put(txtCustNic, nicPattern);
        allValidations.put(txtCustAddress, addressPattern);
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer c1=new Customer(txtCustId.getText(),txtCustName.getText(),txtCustAddress.getText(),txtCustContact.getText(),txtCustNic.getText());


        if (new CustomerController().updateCustomer(c1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
    }

    private void setData(Customer c1) {
        txtCustId.setText(c1.getCustomerId());
        txtCustName.setText(c1.getCustomerName());
        txtCustAddress.setText(c1.getAddress());
        txtCustContact.setText(c1.getContact());
        txtCustNic.setText(c1.getNic());
    }

    public void searchCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = txtCustId.getText();

        Customer c1= new CustomerController().getCustomer(customerId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean){
            btnUpdate.setDisable(false);
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
        btnUpdate.setDisable(true);
    }
}
