package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import model.Advance;
import model.Order;
import model.Payment;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class UpdateCustomerPaymentFormController {
    public JFXComboBox cmbPayID;
    public JFXTextField txtCustId;
    public JFXTextField txtTotalKg;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtTotalPayment;
    public JFXTextField txtAdvance;
    public JFXTextField txtFertilizer;
    public JFXTextField txtDate;

    public void initialize(){
        loadDate();
        try {
            loadPaymentIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbPayID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setPaymentData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(f1.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setPaymentData(String id) throws SQLException, ClassNotFoundException {
        Payment p1=new CustomerController().getPaymentData(id);
        if (p1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else{
            txtCustId.setText(p1.getCustomerID());
            txtTotalKg.setText(String.valueOf(p1.getTotalQty()));
            txtUnitPrice.setText(String.valueOf(p1.getUnitPrice()));
            txtTotalPayment.setText(String.valueOf(p1.getTotPayment()));
            setAdvanceData(p1.getpDate());
        }
    }

    private void setAdvanceData(String date) throws SQLException, ClassNotFoundException {
        Advance a1=new CustomerController().getAdvanceDetails(txtCustId.getText(),date);
        ObservableList<Order> o1 =new CustomerController().getOrderData(txtCustId.getText(),date);
        double tot=calculateCost(o1);
        if (a1==null) {
            txtAdvance.setText(String.valueOf(0.0));
            txtFertilizer.setText(String.valueOf(tot));
        }else if(o1==null) {
            txtFertilizer.setText(String.valueOf(0.0));
            txtAdvance.setText(String.valueOf(a1.getAdvancePrice()));
        }else{
            txtAdvance.setText(String.valueOf(a1.getAdvancePrice()));
            txtFertilizer.setText(String.valueOf(tot));
        }
    }

    private double calculateCost(ObservableList<Order> o1) {
        double ttl=0;
        for (Order tm:o1
        ) {
            ttl+=tm.getTotal();
        }
        return ttl;
    }

    private void loadPaymentIds() throws SQLException, ClassNotFoundException {
        List<String> payIds=new CustomerController().getPaymentIDS();
        cmbPayID.getItems().addAll(payIds);
    }

    public void upadtePaymentsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Payment p1=new Payment(cmbPayID.getValue().toString(),txtCustId.getText(),Double.parseDouble(txtTotalKg.getText()),Double.parseDouble(txtUnitPrice.getText()),Double.parseDouble(txtTotalPayment.getText()),txtDate.getText());
        if(new CustomerController().updateCustomerPayment(p1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Try Again.").show();
        }
        
    }

    public void calculateTotalKg(ActionEvent actionEvent) {
        double advancePrice=Double.parseDouble(txtAdvance.getText());
        double fertilizerPrice=Double.parseDouble(txtFertilizer.getText());
        double kg=Double.parseDouble(txtTotalKg.getText());
        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        double totalPay=kg*unitPrice-advancePrice-fertilizerPrice;
        txtTotalPayment.setText(String.valueOf(totalPay));
    }
}
