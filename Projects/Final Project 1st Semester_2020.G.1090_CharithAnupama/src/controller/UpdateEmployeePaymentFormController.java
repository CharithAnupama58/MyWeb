package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Employee;
import model.Salary;

import java.sql.SQLException;
import java.util.List;

public class UpdateEmployeePaymentFormController {
    public JFXComboBox cmbSalaryId;
    public JFXTextField txtEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpWorkingDays;
    public JFXTextField txtPriceForADay;
    public JFXTextField txtTotalSalary;
    public JFXTextField txtMonth;

    public void initialize(){
        try {
            loadSalaryIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbSalaryId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setSalaryData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setSalaryData(String id) throws SQLException, ClassNotFoundException {
        Salary s1=new EmployeeController().getTotalSalary(id);
        if (s1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Set.").show();
        }else{
            txtEmpId.setText(s1.getEmpId());
            txtEmpWorkingDays.setText(String.valueOf(s1.getWorkingDayCount()));
            txtPriceForADay.setText(String.valueOf(s1.getPriceForADay()));
            txtTotalSalary.setText(String.valueOf(s1.getSalary()));
            txtMonth.setText(s1.getMonth());
            setEmpName(txtEmpId.getText());
        }
    }
    private void setEmpName(String id) throws SQLException, ClassNotFoundException {
        Employee e1=new EmployeeController().getEmployee(id);
        txtEmpName.setText(e1.getName());

    }

    private void loadSalaryIds() throws SQLException, ClassNotFoundException {
        List<String> salaryIds=new EmployeeController().getSalaryIds();
        cmbSalaryId.getItems().addAll(salaryIds);
    }

    public void updateEmployeePaymentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Salary s1=new Salary(cmbSalaryId.getValue().toString(),txtEmpId.getText(),txtMonth.getText(),Double.parseDouble(txtPriceForADay.getText()),Integer.parseInt(txtEmpWorkingDays.getText()),Double.parseDouble(txtTotalSalary.getText()));
        if(new EmployeeController().updateTotalSalary(s1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again.").show();
        }
    }

    public void calculateTotalSalary(ActionEvent actionEvent) {
        double daySalary=Double.parseDouble(txtPriceForADay.getText());
        int count=Integer.parseInt(txtEmpWorkingDays.getText());
        double tot=daySalary*count;
        txtTotalSalary.setText(String.valueOf(tot));
    }
}
