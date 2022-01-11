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
import model.Fertilizer;
import model.Order;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MakeFertilizerOrderFormController {
    public JFXComboBox cmbCustomerID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtContact;
    public JFXComboBox cmbFertID;
    public JFXTextField txtFertName;
    public JFXTextField txtQuantity;
    public JFXTextField txtTotalPrice;
    public JFXTextField txtFertUnitPrice;
    public JFXTextField txtOrderID;
    public JFXTextField txtDate;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern quantityPattern = Pattern.compile("^[1-9][0-9]*{2}?$");
    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtQuantity, quantityPattern);
    }


    public void initialize(){
        validateInit();
        setOrderId();
        loadDateAndTime();
        try {
            loadCustomerIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadFertilizeIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbFertID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setFertilizerData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadDateAndTime() {
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

    private void setOrderId() {
        try {
            txtOrderID.setText(new CustomerController().getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setFertilizerData(String id) throws SQLException, ClassNotFoundException {
        Fertilizer f1=new FertilizerController().getFertilizer(id);

        if (f1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else{
            txtFertName.setText(f1.getFertilizerName());
            txtFertUnitPrice.setText(String.valueOf(f1.getUnitPrice()));
        }
    }

    private void setCustomerData(String id) throws SQLException, ClassNotFoundException {
        Customer c1=new CustomerController().getCustomer(id);
        if (c1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else{
            txtCustomerName.setText(c1.getCustomerName());
            txtContact.setText(c1.getContact());
        }
    }

    private void loadFertilizeIds() throws SQLException, ClassNotFoundException {
        List<String> fertilizerIds=new FertilizerController().getFertilizerIds();
        cmbFertID.getItems().addAll(fertilizerIds);
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds=new CustomerController().getCustomerIds();
        cmbCustomerID.getItems().addAll(customerIds);
    }

    public void saveOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        double unitPrice=Double.parseDouble(txtFertUnitPrice.getText());
        int qty=Integer.parseInt(txtQuantity.getText());
        double total=unitPrice*qty;
        if (txtCustomerName.getText().equals(null)){
            new Alert(Alert.AlertType.WARNING,"TextFields are Empty.").show();
        }else {
            Order o1=new Order(txtOrderID.getText(),cmbCustomerID.getValue().toString(),cmbFertID.getValue().toString(),unitPrice,qty,total,txtDate.getText());
            new CustomerController().saveOrder(o1);
            new Alert(Alert.AlertType.CONFIRMATION,"Record Saved.").show();
            setOrderId();
            txtCustomerName.clear();
            txtContact.clear();
            txtFertName.clear();
            txtTotalPrice.clear();
            txtQuantity.clear();
            txtFertUnitPrice.clear();

        }
    }

    public void loadPaymentOnAction(ActionEvent actionEvent) {
        double unitPrice=Double.parseDouble(txtFertUnitPrice.getText());
        int qty=Integer.parseInt(txtQuantity.getText());
        double total=unitPrice*qty;
        txtTotalPrice.setText(total+"");
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
