package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Advance;
import model.Customer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AdvanceFormController {
    public JFXComboBox cmbCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerContact;
    public JFXTextField txtAdvancePrice;
    public Label lblAdvanceID;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane advancePageContext;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern advancePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtAdvancePrice, advancePattern);
    }


    public void initialize(){
        validateInit();
        loadDateAndTime();
        setAdvanceID();

        try {
            loadCustomerIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setCustomerData(String id) throws SQLException, ClassNotFoundException {
        Customer c1=new CustomerController().getCustomer(id);
        if (c1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.");
        }else{
            txtCustomerName.setText(c1.getCustomerName());
            txtCustomerContact.setText(c1.getContact());
        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds=new CustomerController().getCustomerIds();
        cmbCustomerId.getItems().addAll(customerIds);
    }
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    private void setAdvanceID() {
        try {
            lblAdvanceID.setText(new CustomerController().getAdvanceID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveAdvanceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Advance a1=new Advance(lblAdvanceID.getText(),Double.parseDouble(txtAdvancePrice.getText()),lblDate.getText(),cmbCustomerId.getValue().toString());

        String customerId=cmbCustomerId.getValue().toString();
        Advance a2=new CustomerController().getAdvanceDetails(customerId,lblDate.getText());

        if (a2==null){
            new CustomerController().saveAdvance(a1);
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setAdvanceID();
            
        }else{
            new Alert(Alert.AlertType.WARNING,"Can't Give an Advance").show();
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

