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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Attendance;
import model.DaySalary;
import model.Employee;
import model.Salary;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sun.util.calendar.BaseCalendar;
import view.tm.AttendanceTM;
import view.tm.ObtainingLeavesTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MakeEmployeePaymentFormController {
    public JFXComboBox cmbEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpContact;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtPriceForADay;
    public JFXTextField txtDayCount;
    public TableView<AttendanceTM> tblAttendance;
    public TableColumn colDate;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colAttendance;
    public Label txtTotSalary;
    public Label txtSalaryID;
    public Label lblDate;
    public Label txtDate;
    public Label txtTime;
    public Label txtYear;
    public Label txtMonth;
    public Label txtDay;
    public JFXButton btnShow;
    public JFXButton btnSave;

    public void initialize(){
        btnShow.setDisable(true);
        btnSave.setDisable(true);

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("EmpID"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));

        setSalaryID();
        loadDateAndTime();





        try {
            loadEmployeeIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM");
        txtYear.setText(f1.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            txtTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setSalaryID() {
        try {
            txtSalaryID.setText(new EmployeeController().getSalaryID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        List<String> employeeIds =new EmployeeController().getEmployeeIds();
        cmbEmpId.getItems().addAll(employeeIds);
    }

    private void setEmployeeData(String id) throws SQLException, ClassNotFoundException {
        Employee e1=new EmployeeController().getEmployee(id);
        DaySalary d1=new EmployeeController().getSalary(id);
        if (e1==null|d1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Results Set.").show();
        }else{
            txtEmpName.setText(e1.getName());
            txtEmpContact.setText(e1.getContact());
            txtEmpAddress.setText(e1.getAddress());
            txtPriceForADay.setText(String.valueOf(d1.getSalary()));
            btnShow.setDisable(false);
        }
    }

    public void loadDataToTable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ObservableList<AttendanceTM> attendanceDetails=new EmployeeController().getAttendanceData(cmbEmpId.getValue().toString());
        if (attendanceDetails.size()==0){
            new Alert(Alert.AlertType.WARNING,"Empty Results Set.").show();
        }else{
            tblAttendance.setItems(attendanceDetails);
            int count=new EmployeeController().getAttendanceCount(cmbEmpId.getValue().toString());
            txtDayCount.setText(String.valueOf(count));
            double priceForADay=Double.parseDouble(txtPriceForADay.getText());
            double totalSalary=priceForADay*count;
            txtTotSalary.setText(String.valueOf(totalSalary));
            btnSave.setDisable(false);
        }
    }

    public void saveRecordOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Salary s1=new Salary(txtSalaryID.getText(),cmbEmpId.getValue().toString(),txtYear.getText(),Double.parseDouble(txtPriceForADay.getText()),Integer.parseInt(txtDayCount.getText()),Double.parseDouble(txtTotSalary.getText()));
        String employeeId=cmbEmpId.getValue().toString();
        Salary s2=new EmployeeController().getMonth(txtYear.getText(),employeeId);
        if (s2==null){
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/EmployeePayment.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);
                ObservableList<AttendanceTM> items = tblAttendance.getItems();
                String name=txtEmpName.getText();
                String contact=txtEmpContact.getText();
                int count=Integer.parseInt(txtDayCount.getText());
                double dSalary=Double.parseDouble(txtPriceForADay.getText());
                double tot=Double.parseDouble(txtTotSalary.getText());
                HashMap map = new HashMap();
                map.put("name", name);
                map.put("contact", contact);
                map.put("count", count);
                map.put("daySalary", dSalary);
                map.put("total", tot);

                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(items.toArray()));
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
            new EmployeeController().saveEmployeePayment(s1);
            new Alert(Alert.AlertType.CONFIRMATION,"Saved.").show();
            setSalaryID();
        }else{
            new Alert(Alert.AlertType.WARNING,"Already Paid.").show();
        }
    }
}
