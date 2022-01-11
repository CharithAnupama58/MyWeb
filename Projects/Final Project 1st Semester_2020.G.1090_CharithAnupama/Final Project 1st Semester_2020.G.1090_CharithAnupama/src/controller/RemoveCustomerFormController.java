package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class RemoveCustomerFormController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerContact;
    public JFXTextField txtCustomerNic;
    public JFXTextField txtCustomerAddress;
    public JFXButton btnRemove;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(C-00)[0-9]{1,3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern contactPattern = Pattern.compile("^[0-9]{10,15}$");
    Pattern nicPattern = Pattern.compile("^[0-9v]{10,15}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");

    public void initialize() {
        validateInit();
    }

    private void validateInit() {
        btnRemove.setDisable(true);
        allValidations.put(txtCustomerId, idPattern);
        allValidations.put(txtCustomerName, namePattern);
        allValidations.put(txtCustomerContact, contactPattern);
        allValidations.put(txtCustomerNic, nicPattern);
        allValidations.put(txtCustomerAddress, addressPattern);
    }

    public void removeCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new CustomerController().deleteCustomer(txtCustomerId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void searchCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = txtCustomerId.getText();

        Customer c1 = new CustomerController().getCustomer(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }
    }

    private void setData(Customer c1) {
        txtCustomerId.setText(c1.getCustomerId());
        txtCustomerName.setText(c1.getCustomerName());
        txtCustomerAddress.setText(c1.getAddress());
        txtCustomerContact.setText(c1.getContact());
        txtCustomerNic.setText(c1.getNic());
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean) {
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

