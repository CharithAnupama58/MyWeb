package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.impl.CustomerBOImpl;
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

public class SaveCustomerFormController {
    public JFXTextField txtID;
    public JFXTextField txtTitle;
    public JFXTextField txtName;
    public JFXTextField txtCity;
    public JFXTextField txtAddress;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(C-00)[0-9]{1,3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern titlePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern cityPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern provincePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern postalCodePattern = Pattern.compile("^^[A-z0-9]{6,30}$");

    public void initialize(){
        validateInit();

    }

    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtID, idPattern);
        allValidations.put(txtTitle, titlePattern);
        allValidations.put(txtName, namePattern);
        allValidations.put(txtAddress, addressPattern);
        allValidations.put(txtCity ,cityPattern);
        allValidations.put(txtProvince ,provincePattern);
        allValidations.put(txtPostalCode ,postalCodePattern);
    }

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CUSTOMER);



    public void saveCustomerOnAction(ActionEvent actionEvent) {
        CustomerDTO c1 = new CustomerDTO(
                txtID.getText(), txtTitle.getText(),
                txtName.getText(), txtAddress.getText(), txtCity.getText(), txtProvince.getText(), txtPostalCode.getText()
        );

        try {
            if (customerBO.ifCustomerExist(txtID.getText())) {
                new Alert(Alert.AlertType.WARNING, "Already Exists.").show();
            } else {
                customerBO.addCustomer(c1);
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Realeased(KeyEvent keyEvent) {
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
