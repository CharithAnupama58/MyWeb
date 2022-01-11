package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import model.BestCustomer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;

public class BestCustomerFormController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtDate;
    public JFXTextField txtCustomerName1;

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
        ObservableList<BestCustomer> customers=new CustomerController().getBestCustomer(txtDate.getText());
        int m=getMax(customers);
        BestCustomer c1=customers.get(m);
        txtCustomerId.setText(c1.getCustomerID());
        txtCustomerName.setText(String.valueOf(c1.getTotKg()));
    }

    private int getMax(ObservableList<BestCustomer> customers) {
        int i=0;
        double max=customers.get(i).getTotKg();
        for (int j=0;j<customers.size();j++){
            if(customers.get(j).getTotKg()>max){
                max=customers.get(j).getTotKg();
                i=j;

            }

        }
        return i;
    }
}
