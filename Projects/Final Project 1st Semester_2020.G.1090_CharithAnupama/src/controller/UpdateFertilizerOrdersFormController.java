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
import model.Order;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class UpdateFertilizerOrdersFormController {
    public JFXTextField txtCustID;
    public JFXTextField txtFertID;
    public JFXTextField txtQuantity;
    public JFXTextField txtTotalPayment;
    public JFXComboBox cmbOrderID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtDate;
    public JFXButton btnUpdate;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern quantityPattern = Pattern.compile("^[1-9][0-9]*{2}?$");
    private void validateInit() {
        btnUpdate.setDisable(true);
        allValidations.put(txtQuantity, quantityPattern);
    }


    public void initialize(){
        validateInit();
        loadDate();
        try {
            loadOrderIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbOrderID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setOrderData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setOrderData(String id) throws SQLException, ClassNotFoundException {
        Order o1 = new CustomerController().getOrder(id);
        if (o1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else{
            txtCustID.setText(o1.getCustomerId());
            txtFertID.setText(o1.getFertilizerId());
            txtQuantity.setText(String.valueOf(o1.getQuantity()));
            txtTotalPayment.setText(String.valueOf(o1.getTotal()));
            txtUnitPrice.setText(String.valueOf(o1.getUnitPriceForAPack()));
            setCustomerName(txtCustID.getText());
        }
    }

    private void setCustomerName(String id) throws SQLException, ClassNotFoundException {
        Customer c1=new CustomerController().getCustomer(id);
        if (c1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else{
            txtCustomerName.setText(c1.getCustomerName());
        }
    }

    private void loadOrderIds() throws SQLException, ClassNotFoundException {
        List<String> orderIds=new CustomerController().getOrderIds();
        cmbOrderID.getItems().addAll(orderIds);
    }

    public void updateOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Order o1=new Order(cmbOrderID.getValue().toString(),txtCustID.getText(),txtFertID.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQuantity.getText()),Double.parseDouble(txtTotalPayment.getText()),txtDate.getText());

        if (new CustomerController().updateOrder(o1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
    }

    public void updatePaymentOnAction(ActionEvent actionEvent) {
        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtQuantity.getText());
        double total=unitPrice*qty;
        txtTotalPayment.setText(String.valueOf(total));
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

