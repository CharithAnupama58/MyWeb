package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Customer;
import model.Employee;
import model.ObtainingLeaves;
import view.tm.BagTM;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static javax.swing.UIManager.getString;

public class ObtainingLeavesPageController {
    public JFXComboBox cmbEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpContact;
    public JFXTextField txtCustName;
    public JFXTextField txtCustContact;
    public JFXTextField txtBagNo;
    public JFXTextField txtKg;
    public TableView<BagTM> tblLeaves;
    public TableColumn colBagNumber;
    public TableColumn colNumberOfKilos;
    public JFXComboBox cmbCustomerID;
    public Label lblObID;
    public TableColumn colWeightOfTheBag;
    public TableColumn colWeightOfLeaves;
    public Label lblDate;
    public Label txtTotal;
    public AnchorPane obContext;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern quantityPattern = Pattern.compile("^[1-9][0-9]$");
    private void validateInit() {
        btnSave.setDisable(true);
        btnAdd.setDisable(true);
        allValidations.put(txtKg, quantityPattern);
    }


    public void initialize(){
        validateInit();
        colBagNumber.setCellValueFactory(new PropertyValueFactory<>("bagNo"));
        colNumberOfKilos.setCellValueFactory(new PropertyValueFactory<>("kilo"));
        colWeightOfTheBag.setCellValueFactory(new PropertyValueFactory<>("bagWeight"));
        colWeightOfLeaves.setCellValueFactory(new PropertyValueFactory<>("leavesWeight"));

        loadDateAndTime();




        try {
            setObtainIds();
            loadCustomerIds();
            loadEmployeeIds();
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
        cmbEmpId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setEmployeeData(newValue.toString());
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
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setBagNo(int tempId){
        tempId = tempId + 1;
        if (tempId < 9) {
            txtBagNo.setText("00" + tempId);
        } else if (tempId < 99) {
            txtBagNo.setText("0" + tempId);
        } else {
            txtBagNo.setText(""+tempId);
        }
    }

    private void setObtainIds() throws SQLException, ClassNotFoundException {
        setBagNo(0);
        lblObID.setText(new CustomerController().getObtainID());
    }

    private void setEmployeeData(String id) throws SQLException, ClassNotFoundException {
        Employee e1=new EmployeeController().getEmployee(id);
        if (e1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else{
            txtEmpName.setText(e1.getName());
            txtEmpContact.setText(e1.getContact());
        }
    }

    private void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        List<String> employeeIds=new EmployeeController().getEmployeeIds();
        cmbEmpId.getItems().addAll(employeeIds);
    }

    private void setCustomerData(String id) throws SQLException, ClassNotFoundException {
        Customer c1=new CustomerController().getCustomer(id);
        if (c1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else{
            txtCustName.setText(c1.getCustomerName());
            txtCustContact.setText(c1.getContact());
        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds=new CustomerController().getCustomerIds();
        cmbCustomerID.getItems().addAll(customerIds);
    }

    ObservableList<BagTM> obList= FXCollections.observableArrayList();

    public void addToTableOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        double bWithLeaves=Double.parseDouble(txtKg.getText());
        double bWeight=4.0;
        double lWeight=bWithLeaves-bWeight;

        if (txtEmpName.getText().trim().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Invalid Customer ID/Employee ID.").show();
        }else{
            BagTM tm=new BagTM(txtBagNo.getText(),bWithLeaves,bWeight,lWeight);
            obList.add(tm);
            tblLeaves.setItems(obList);
            calculateCost();
            new Alert(Alert.AlertType.CONFIRMATION,"Success.").show();
            setBagNo(Integer.parseInt(txtBagNo.getText()));
        }
    }

    private void calculateCost() {
        double total=0;
        for (BagTM tm:obList
             ) {
            total+=tm.getLeavesWeight();
        }
        txtTotal.setText(String.valueOf(total));
    }

    public void saveDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ObtainingLeaves b1=new ObtainingLeaves(lblObID.getText(),cmbCustomerID.getValue().toString(),cmbEmpId.getValue().toString(),lblDate.getText(),Double.parseDouble(txtTotal.getText()));
        new CustomerController().saveObtainingLeaves(b1);
        new Alert(Alert.AlertType.CONFIRMATION,"Saved.").show();
        setObtainIds();
        txtEmpName.clear();
        txtEmpContact.clear();
        txtCustName.clear();
        txtCustContact.clear();
        txtTotal.setText("");
        txtBagNo.clear();
        txtKg.clear();
        obList.clear();
        tblLeaves.refresh();
        setBagNo(0);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean){
            btnAdd.setDisable(false);
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
        btnAdd.setDisable(true);
        btnSave.setDisable(true);
    }
}

