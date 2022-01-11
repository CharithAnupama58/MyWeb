package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.PurchaseOrderBO;
import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PurchaseOrderBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailsDAOImpl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.CartTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddNewCustomerPageController {
    public Label lblOrderId;
    public JFXComboBox cmbCustomerIds;
    public JFXTextField txtTitle;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public JFXComboBox cmbItemIds;
    public JFXTextField txtDescription;
    public JFXTextField txtQty;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtPackSize1;
    public TableView<CartTM> tblCart;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public Label txtTtl;
    public Label lbldate;
    public Label lblTime;
    public TableColumn colDiscount;
    public Label lblOrderDiscount;
    public JFXButton btnOrder;

    int cartSelectedRowForRemove = -1;
    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PURCHASE_ORDER);

    public void initialize() {

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));


        loadDateAndTime();
        setOrderId();

        try {
            loadCustomerIds();
            loadItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                tblCart.getItems().clear();
                setCustomerData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbItemIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        ItemDTO i1 = purchaseOrderBO.searchItem(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(i1.getDescription());
            txtPackSize1.setText(i1.getPackSize());
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = purchaseOrderBO.getItemIds();
        cmbItemIds.getItems().addAll(itemIds);
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = purchaseOrderBO.getCustomerIds();
        cmbCustomerIds.getItems().addAll(customerIds);
    }

    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        CustomerDTO c1 = purchaseOrderBO.searchCustomer(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtTitle.setText(c1.getTitle());
            txtName.setText(String.valueOf(c1.getCustomerName()));
            txtAddress.setText(c1.getAddress());
            txtCity.setText(c1.getCity());
            txtProvince.setText(c1.getProvince());
            txtPostalCode.setText(c1.getPostalCode());
        }
    }

    private void setOrderId() {

        try {

            lblOrderId.setText(purchaseOrderBO.generateNewOrderId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lbldate.setText(f.format(date));

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

    public void clearOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }

    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent) {
        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        double total = qtyForCustomer * unitPrice;
        double discount = 0;
        int qty = qtyOnHand - qtyForCustomer;

        if (qtyOnHand < qtyForCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }
        if (qtyForCustomer > 10) {
            discount = 50;
            total = total - 50;
        } else {
            discount = 0.0;
        }


        CartTM tm = new CartTM(
                (String) cmbItemIds.getValue(),
                description,
                qtyForCustomer,
                unitPrice,
                total,
                discount

        );

        int rowNumber = isExists(tm);

        if (rowNumber == -1) {// new Add
            obList.add(tm);
        } else {
            // update
            CartTM temp = obList.get(rowNumber);
            CartTM newTm = new CartTM(
                    temp.getItemId(),
                    temp.getDescription(),
                    temp.getQty() + qtyForCustomer,
                    unitPrice,
                    total + temp.getTotal(),
                    temp.getDiscount()
            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblCart.setItems(obList);
        calculateCost();
        btnOrder.setDisable(false);
    }

    void calculateCost() {
        double ttl = 0;
        for (CartTM tm : obList
        ) {
            ttl += tm.getTotal();
        }

        double orderDiscount = 0.0;
        if (ttl >= 2000) {
            orderDiscount = 200;
            ttl = ttl - orderDiscount;
            txtTtl.setText(String.valueOf(ttl));
        } else {
            orderDiscount = 0.0;
            txtTtl.setText(String.valueOf(ttl));
        }
        lblOrderDiscount.setText(orderDiscount + " /=");
    }

    private int isExists(CartTM tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemId().equals(obList.get(i).getItemId())) {
                return i;
            }
        }
        return -1;
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        ArrayList<ItemDetailsDTO> items = new ArrayList<>();
        double total = 0;
        for (CartTM tempTm : obList
        ) {
            total += tempTm.getTotal();
            items.add(new ItemDetailsDTO(lblOrderId.getText(),tempTm.getItemId(), tempTm.getUnitPrice(),
                    tempTm.getQty(), 0.0));
        }

        OrderDTO orderDTO = new OrderDTO(lblOrderId.getText(), cmbCustomerIds.getValue().toString(),
                lbldate.getText(),
                lblTime.getText(),
                total,
                items
        );
        boolean b = saveOrder(orderDTO, items);

        if (b) {
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            setOrderId();
            btnOrder.setDisable(true);
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/CustomerBill.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);
                ObservableList<CartTM> item = tblCart.getItems();
                double tot=Double.parseDouble(txtTtl.getText());
                HashMap map = new HashMap();
                map.put("total", tot);// id= param name of report // customerID= input value of text field


                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(item.toArray()));
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

    }

    private boolean saveOrder(OrderDTO orderDTO, ArrayList<ItemDetailsDTO> items) {
        try {
            return purchaseOrderBO.purchaseOrder(orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }



    public ItemDTO findItem(String code) {
        try {
            return purchaseOrderBO.searchItem(code);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
