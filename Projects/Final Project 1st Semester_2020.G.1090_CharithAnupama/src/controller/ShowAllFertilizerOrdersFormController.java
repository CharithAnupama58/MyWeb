package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.OrderTM;

import java.sql.SQLException;
import java.util.Optional;

public class ShowAllFertilizerOrdersFormController {
    public TableView<OrderTM> tblOrder;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colFertId;
    public TableColumn colQuantity;
    public TableColumn colUnitPrice;
    public TableColumn colTotalPrice;
    public TableColumn colDelete;
    public JFXTextField txtOrderID;
    public JFXTextField txtCustomerID;
    public JFXTextField txtFertID;
    public JFXTextField txtTotal;
    int SelectedRowForRemove = -1;

    public void initialize() throws SQLException, ClassNotFoundException {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colFertId.setCellValueFactory(new PropertyValueFactory<>("fertilizerId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPriceForAPack"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            SelectedRowForRemove= (int) newValue;
        });
        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadOrderData(newValue);
        });


       obList=new CustomerController().getAllOrders();
        tblOrder.setItems(obList);
    }
    ObservableList<OrderTM> obList = FXCollections.observableArrayList();

    private void loadOrderData(OrderTM o1) {
        txtOrderID.setText(o1.getOrderId());
        txtCustomerID.setText(o1.getCustomerId());
        txtFertID.setText(o1.getFertilizerId());
        txtTotal.setText(String.valueOf(o1.getTotal()));
    }

    public void removeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (SelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            ButtonType yes=new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
            ButtonType no=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Student?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no)==yes){
                new CustomerController().deleteOrder(txtOrderID.getText());
                obList.remove(SelectedRowForRemove);
                tblOrder.refresh();
            }else{
            }
        }
    }
}
