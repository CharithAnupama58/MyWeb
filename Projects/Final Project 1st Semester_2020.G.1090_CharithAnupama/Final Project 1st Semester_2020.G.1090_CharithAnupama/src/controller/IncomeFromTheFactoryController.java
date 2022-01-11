package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import model.Factory;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IncomeFromTheFactoryController {
    public JFXTextField txtTotKg;
    public JFXTextField txtUnitPricePerKg;
    public JFXTextField txtTotalIncome;
    public JFXTextField txtDate;
    public JFXTextField txtMonthlyIncome;

    public  void initialize(){
        loadDate();
    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM");
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

    public void loadDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        double totKg=new CustomerController().getTotalKg(txtDate.getText());
        txtTotKg.setText(String.valueOf(totKg));
    }

    public void calculateCost(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        double totalIncome=calculateTotal();
        txtTotalIncome.setText(String.valueOf(totalIncome));
        double fuel=new CustomerController().getFuelPrice(txtDate.getText());
        double custPay=new CustomerController().getCustPayPrice(txtDate.getText());
        double empPay=new CustomerController().getEmpPay(txtDate.getText());
        double totalMonthlyIncome=totalIncome-fuel-custPay-empPay;
        txtMonthlyIncome.setText(String.valueOf(totalMonthlyIncome));
    }
    public double calculateTotal(){
        double unitPrice=Double.parseDouble(txtUnitPricePerKg.getText());
        double totalKg=Double.parseDouble(txtTotKg.getText());
        double totalIncome=unitPrice*totalKg;
        return totalIncome;

    }
}
