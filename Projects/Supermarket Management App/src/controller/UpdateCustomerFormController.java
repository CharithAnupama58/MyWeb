package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.PurchaseOrderBO;
import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.PurchaseOrderBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import dto.CustomerDTO;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateCustomerFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtTitle;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CUSTOMER);
    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PURCHASE_ORDER);
    public JFXButton btnUpdate;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(C-00)[0-9]{1,3}$");


    public void initialize(){
        validateInit();

    }

    private void validateInit() {
        btnUpdate.setDisable(true);
        allValidations.put(txtId, idPattern);
    }


        public void searchCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String customerId = txtId.getText();

        CustomerDTO c1= purchaseOrderBO.searchCustomer(customerId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDTO c1 = new CustomerDTO(
                txtId.getText(),txtTitle.getText(),
                txtName.getText(),txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText()
        );



        if (customerBO.updateCustomer(c1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();

    }
    void setData(CustomerDTO c){
        txtId.setText(c.getCustomerId());
        txtTitle.setText(c.getTitle());
        txtName.setText(c.getCustomerName());
        txtAddress.setText(c.getCustomerName());
        txtCity.setText(c.getCity());
        txtProvince.setText(c.getProvince());
        txtPostalCode.setText(c.getPostalCode());

    }

    public void textFields_Key_Realeased(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean){
            btnUpdate.setDisable(false);
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField textField = (TextField) response;

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
