package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import model.Attendance;
import model.Employee;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class EmpAttendanceFormController {
    public JFXComboBox cmbEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtEmpContact;
    public JFXTextField txtDate;
    public JFXTextField txtTime;
    public JFXButton btnSave;

    public void initialize(){
        loadDateAndTime();
        btnSave.setDisable(true);

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

    private void setEmployeeData(String id) throws SQLException, ClassNotFoundException {
        Employee e1=new EmployeeController().getEmployee(id);
        if (e1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Results Set.");
        }else{
            txtEmpName.setText(e1.getName());
            txtEmpAddress.setText(e1.getAddress());
            txtEmpContact.setText(e1.getContact());
            btnSave.setDisable(false);

        }
    }

    private void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        List<String> employeeIds =new EmployeeController().getEmployeeIds();
        cmbEmpId.getItems().addAll(employeeIds);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(f.format(date));

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

    public void saveAttendanceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Attendance at1=new Attendance(cmbEmpId.getValue().toString(),txtEmpName.getText(),txtDate.getText());

        String attendanceDate=txtDate.getText();
        String employeeId=cmbEmpId.getValue().toString();
        Attendance at3=new EmployeeController().getAttendance(employeeId,attendanceDate);
        if (at3==null){
            new EmployeeController().saveAttendance(at1);
            new Alert(Alert.AlertType.CONFIRMATION,"Saved.").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Attendance Already Exists On This Day.").show();
        }
    }
}
