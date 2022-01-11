package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.ObtainingLeavesTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MakeCustomerPaymentFormController {
    public JFXTextField txtCustName;
    public JFXTextField txtCustContact;
    public JFXTextField txtCustAddress;
    public JFXTextField txtCustFertilizerPrice;
    public JFXTextField txtCustAdvancePrice;
    public JFXTextField txtUnitPriceForATeakg;
    public JFXTextField txtTotalPayment;
    public TableView<ObtainingLeavesTM> tblLeaves;
    public TableColumn colObId;
    public TableColumn colCustomerId;
    public TableColumn colEmpId;
    public TableColumn colDate;
    public TableColumn colQuantity;
    public JFXTextField txtTotalKg;
    public JFXTextField txtPayId;
    public JFXComboBox cmbCustomerID;
    public JFXTextField txtCustID;
    public JFXTextField txtDate;
    public JFXButton btnShow;
    public JFXButton btnPayment;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    private void validateInit() {
        btnShow.setDisable(true);
        btnPayment.setDisable(true);
        allValidations.put(txtUnitPriceForATeakg, pricePattern);
    }

    public void initialize(){
        validateInit();
        colObId.setCellValueFactory(new PropertyValueFactory<>("obID"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("obDate"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        loadDateAndTime();


        try {
            loadCustomerIds();
            setPayID();
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

    private void loadDateAndTime() {
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

    private void setPayID() throws SQLException, ClassNotFoundException {
        txtPayId.setText(new CustomerController().getPaymentID());
    }

    private void setCustomerData(String id) throws SQLException, ClassNotFoundException {
        Customer c1=new CustomerController().getCustomer(id);
        Advance a1=new CustomerController().getAdvanceDetails(id,txtDate.getText());
        ObservableList<Order> o1=new CustomerController().getOrderData(id,txtDate.getText());
        double total=calculateCost(o1);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Set.").show();
        }else if (a1==null) {
            txtCustAdvancePrice.setText(String.valueOf(0.0));
            txtCustName.setText(c1.getCustomerName());
            txtCustContact.setText(c1.getContact());
            txtCustAddress.setText(c1.getAddress());
            txtCustFertilizerPrice.setText(String.valueOf(total));
            btnShow.setDisable(false);
        }else if (o1.size()==0){
            txtCustFertilizerPrice.setText(String.valueOf(0.0));
            txtCustName.setText(c1.getCustomerName());
            txtCustContact.setText(c1.getContact());
            txtCustAddress.setText(c1.getAddress());
            txtCustAdvancePrice.setText(String.valueOf(a1.getAdvancePrice()));
            btnShow.setDisable(false);

        }else {
            txtCustName.setText(c1.getCustomerName());
            txtCustContact.setText(c1.getContact());
            txtCustAddress.setText(c1.getAddress());
            txtCustAdvancePrice.setText(String.valueOf(a1.getAdvancePrice()));
            txtCustFertilizerPrice.setText(String.valueOf(total));
            btnShow.setDisable(false);
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

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds=new CustomerController().getCustomerIds();
        cmbCustomerID.getItems().addAll(customerIds);
    }

    public void loadDataToTable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ObservableList<ObtainingLeavesTM> leaves=new CustomerController().getAllLeaves(cmbCustomerID.getValue().toString(),txtDate.getText());
        if (leaves.size()==0){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else {
            tblLeaves.setItems(leaves);
            double total = calculateTotalKg(leaves);
            txtTotalKg.setText(String.valueOf(total));
            double unitPriceForKg = Double.parseDouble(txtUnitPriceForATeakg.getText());
            double totKg = Double.parseDouble(txtTotalKg.getText());
            double advance=Double.parseDouble(txtCustAdvancePrice.getText());
            double orderPrice=Double.parseDouble(txtCustFertilizerPrice.getText());
            double totPayment = unitPriceForKg * totKg-advance-orderPrice;
            txtTotalPayment.setText(String.valueOf(totPayment));
        }

    }

    private double calculateTotalKg(ObservableList<ObtainingLeavesTM> leaves) {
        double ttl=0;
        for (ObtainingLeavesTM tm:leaves
        ) {
            ttl+=tm.getQuantity();
        }
        return ttl;
    }

    public void clearTextFields(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Payment p1=new Payment(txtPayId.getText(),cmbCustomerID.getValue().toString(),Double.parseDouble(txtTotalKg.getText()),Double.parseDouble(txtUnitPriceForATeakg.getText()),Double.parseDouble(txtTotalPayment.getText()),txtDate.getText());
        String customerId=cmbCustomerID.getValue().toString();
        Payment p2=new CustomerController().getMonth(txtDate.getText(),customerId);
        if (p2==null){
            new CustomerController().saveCustomerPayment(p1);
            new Alert(Alert.AlertType.CONFIRMATION,"Saved.").show();
            setPayID();
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/CustomerPayment.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);
                ObservableList<ObtainingLeavesTM> items = tblLeaves.getItems();
                String name=txtCustName.getText();
                double totKilo=Double.parseDouble(txtTotalKg.getText());
                double unitPrice=Double.parseDouble(txtUnitPriceForATeakg.getText());
                double advance=Double.parseDouble(txtCustAdvancePrice.getText());
                double fertilizer=Double.parseDouble(txtCustFertilizerPrice.getText());
                double payment=Double.parseDouble(txtTotalPayment.getText());
                HashMap map = new HashMap();
                map.put("Name", name);// id= param name of report // customerID= input value of text field
                map.put("Leaves Kilo", totKilo);
                map.put("Unit Price", unitPrice);
                map.put("Payment", payment);
                map.put("advance", advance);
                map.put("FertPrice", fertilizer);

                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(items.toArray()));
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Already Paid.").show();
        }
    }

    public void calculatePayment(ActionEvent actionEvent) {
        double unitPriceForKg = Double.parseDouble(txtUnitPriceForATeakg.getText());
        double totKg = Double.parseDouble(txtTotalKg.getText());
        double advance=Double.parseDouble(txtCustAdvancePrice.getText());
        double orderPrice=Double.parseDouble(txtCustFertilizerPrice.getText());
        double totPayment = unitPriceForKg * totKg-advance-orderPrice;
        txtTotalPayment.setText(String.valueOf(totPayment));
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean){
            btnPayment.setDisable(false);
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
        btnPayment.setDisable(true);
    }

    public void generateReport(MouseEvent mouseEvent) {



    }
}
