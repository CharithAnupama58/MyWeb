package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import bo.custom.PurchaseOrderBO;
import bo.custom.impl.PurchaseOrderBOImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDetailsDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RemoveOrderFormController {
    public JFXComboBox cmbOrderID;
    public JFXTextField txtCustID;
    public JFXTextField txtOrderDate;
    public JFXTextField txtOrderTime;
    public JFXTextField txtOrderTotal;
    final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PURCHASE_ORDER);

    public void initialize() {
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
    private void loadOrderIds() throws SQLException, ClassNotFoundException {
        List<String> Ids = purchaseOrderBO.getOrderIds();
        cmbOrderID.getItems().addAll(Ids);
    }

    private void setOrderData(String orderId) throws SQLException, ClassNotFoundException {
        OrderDTO o1 =purchaseOrderBO.searchOrder(orderId);
        if (o1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtCustID.setText(o1.getCustomerId());
            txtOrderDate.setText(o1.getOrderDate());
            txtOrderTime.setText(o1.getOrderTime());
            txtOrderTotal.setText(String.valueOf(o1.getCost()));

        }
    }

    public void deleteOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            ArrayList<ItemDetailsDTO> item =purchaseOrderBO.getOrderItems(cmbOrderID.getValue().toString());
            purchaseOrderBO.deleteOrder(cmbOrderID.getValue().toString(),item);
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();

        }
    }
