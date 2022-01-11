package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import bo.custom.PurchaseOrderBO;
import bo.custom.impl.PurchaseOrderBOImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.OrderDAO;
import dao.custom.impl.OrderDAOImpl;
import javafx.event.ActionEvent;
import dto.CustomerDTO;
import dto.CustomerIncomeDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerWiseIncomePageFormController {
    public JFXComboBox cmbCustomerID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtIncome;
    final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PURCHASE_ORDER);


    public void initialize(){

        try {
            loadCustomerIds();
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
    }


    private void setCustomerData(String id) throws SQLException, ClassNotFoundException {
        CustomerDTO c1 = purchaseOrderBO.searchCustomer(id);
        double income=purchaseOrderBO.getCustomerIncome(id);
        txtCustomerName.setText(c1.getCustomerName());
        txtIncome.setText(String.valueOf(income));
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = purchaseOrderBO.getCustomerIds();
        cmbCustomerID.getItems().addAll(customerIds);
    }

    public void clearTextFields(ActionEvent actionEvent) {
        txtIncome.clear();
        txtCustomerName.clear();
    }
}
