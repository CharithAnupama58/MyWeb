package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import bo.custom.PurchaseOrderBO;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PurchaseOrderBOImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailsDAO;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDetailsDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import javafx.scene.layout.AnchorPane;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UpdateOrderFormController {
    public JFXComboBox cmbOrderID;
    public JFXTextField txtcustID;
    public JFXTextField txtOrderDate;
    public JFXTextField txtOrderTime;
    public Label lblTotal;
    public JFXTextField txtDescription;
    public JFXTextField txtTotal;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtDiscount;
    public TableView<CartTM> tblCart;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public JFXComboBox cmbItemID;
    public JFXTextField txtPackSize;
    public JFXTextField txtPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQtyForCust;
    public JFXTextField txtItemId;
    final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PURCHASE_ORDER);
    private final ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);


    int cartSelectedRowForRemove = -1;

    public void initialize() {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        //colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {
            loadOrderIds();
            loadItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbOrderID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                tblCart.getItems().clear();
                setOrderData(newValue.toString());
                setCartData(purchaseOrderBO.getOrderItems(cmbOrderID.getValue().toString()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        tblCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadCartData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
        cmbItemID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        ItemDTO i1 = purchaseOrderBO.searchItem(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            //txtDescription.setText(i1.getDescription());
            //txtPackSize.setText(i1.getPackSize());
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtPrice.setText(String.valueOf(i1.getUnitPrice()));
        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = purchaseOrderBO.getItemIds();
        cmbItemID.getItems().addAll(itemIds);
    }

    private void loadCartData(CartTM t) throws SQLException, ClassNotFoundException {
        cmbItemID.setValue(t.getItemId());
        txtQtyForCust.setText(String.valueOf(t.getQty()));
        txtPrice.setText(String.valueOf(t.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(itemBO.getQtyOnHand(t.getItemId())));
       // txtDiscount.setText(String.valueOf(t.getDiscount()));
    }

    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    private void setCartData(ArrayList<ItemDetailsDTO> itemList) throws SQLException, ClassNotFoundException {
        itemList.forEach(e -> {
            double total = (e.getQtyForSell() * e.getUnitPrice() - e.getDiscount());
            CartTM tm = new CartTM(e.getItemCode(), null, e.getQtyForSell(), e.getUnitPrice(), total, e.getDiscount());
            obList.add(tm);
        });
        tblCart.setItems(obList);
    }

    private void setOrderData(String orderId) throws SQLException, ClassNotFoundException {
        OrderDTO o1 = purchaseOrderBO.searchOrder(orderId);
        if (o1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtcustID.setText(o1.getCustomerId());
            txtOrderDate.setText(o1.getOrderDate());
            txtOrderTime.setText(o1.getOrderTime());
            lblTotal.setText(String.valueOf(o1.getCost()));
        }
    }

    ObservableList<CartTM> deleteItemsList=FXCollections.observableArrayList();

    private void loadOrderIds() throws SQLException, ClassNotFoundException {
        List<String> Ids = purchaseOrderBO.getOrderIds();
        cmbOrderID.getItems().addAll(Ids);
    }


    public void updateOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetailsDTO> items= new ArrayList<>();
       Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Warning");
       alert.setHeaderText("Are you sure");
       alert.setContentText("Select okay or cancel this alert");
        Optional<ButtonType> result=alert.showAndWait();
        String oid=cmbOrderID.getValue().toString();
        if (!result.isPresent()){

        }else if (result.get()==ButtonType.OK){
            for (CartTM tempTm:obList
                 ) {
                items.add(new ItemDetailsDTO(cmbOrderID.getValue().toString(),tempTm.getItemId(),tempTm.getUnitPrice(),tempTm.getQty(),tempTm.getDiscount()));

            }

            OrderDTO orderDTO =new OrderDTO(cmbOrderID.getSelectionModel().getSelectedItem().toString(),txtcustID.getText(),txtOrderDate.getText(),txtOrderTime.getText(),Double.parseDouble(lblTotal.getText()),items);

            if (purchaseOrderBO.deleteOrder(oid,purchaseOrderBO.getOrderItems(oid))&purchaseOrderBO.purchaseOrder(orderDTO)){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
            }else if (result.get()==ButtonType.CANCEL){

            }

        }
    public void clearOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            deleteItemsList.add(obList.get(cartSelectedRowForRemove));
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }
    void calculateCost(){
        double ttl=0;
        for (CartTM tm:obList
        ) {
            ttl+=tm.getTotal();
        }
        lblTotal.setText(String.valueOf(ttl));
    }

    public void addItemOnAction(ActionEvent actionEvent) {
        try {
            int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
            double unitPrice = Double.parseDouble(txtPrice.getText());
            int qtyForCustomer = Integer.parseInt(txtQtyForCust.getText());
            double total = qtyForCustomer * unitPrice;
            double discount =0;

       /* if (qtyOnHand<qtyForCustomer){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
            return;
        }*/
            if (qtyForCustomer>10){
                discount=50;
                total=total-50;
            }else{
                discount=0.0;
            }


            CartTM tm = new CartTM(
                    cmbItemID.getValue().toString(),
                    null,
                    qtyForCustomer,
                    unitPrice,
                    total,
                    discount=0.0

            );

            int rowNumber=isExists(tm);

            if (rowNumber==-1){// new Add
                obList.add(tm);
            }else{
                // update
                CartTM temp = obList.get(rowNumber);
                CartTM newTm = new CartTM(
                        temp.getItemId(),
                        temp.getDescription(),
                        qtyForCustomer,
                        unitPrice,
                        total,
                        discount=0.0
                );

           /* if (qtyOnHand<temp.getQty()){
                new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
                return;
            }*/

                obList.remove(rowNumber);
                obList.add(newTm);
            }
            tblCart.setItems(obList);
            calculateCost();

        } catch (NullPointerException throwables) {
            throwables.printStackTrace();
        }
    }

    private int isExists(CartTM tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemId().equals(obList.get(i).getItemId())){
                return i;
            }
        }
        return -1;
    }
}
