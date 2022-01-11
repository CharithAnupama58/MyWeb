package controller;

import business.BOFactory;
import business.custom.CourseBO;
import business.custom.RegisterDetailsBO;
import business.custom.StudentBO;
import business.custom.impl.CourseBOImpl;
import business.custom.impl.RegisterDetailsBOImpl;
import business.custom.impl.StudentBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CourseDTO;
import dto.RegisterDetailsDTO;
import dto.StudentDTO;
import entity.Course;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class CourseRegistrationFormController {
    public JFXComboBox cmbStudentID;
    public JFXTextField txtStName;
    public JFXTextField txtCourseName;
    public JFXTextField txtDuration;
    public JFXTextField txtCourseFee;
    public JFXComboBox cmbCourseID;
    public Label lblDate;
    private final StudentBO studentBO=(StudentBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.STUDENT);
    private final CourseBO courseBO=(CourseBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.COURSE);
    private final RegisterDetailsBO registerDetailsBO=(RegisterDetailsBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.REGISTERDETAILS);
    public JFXButton btnSave;
    public Label txtRegID;

    public void initialize(){
        btnSave.setDisable(true);

        try {
            loadStudentIds();
            loadCourseIds();
            loadDateAndTime();
            setRegID();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbStudentID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setStudentData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        cmbCourseID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            try {
                setCourseData(newValue.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
    }

    private void setRegID() throws SQLException, ClassNotFoundException {
        txtRegID.setText(registerDetailsBO.getRegID());
    }

    private void setCourseData(String id) throws SQLException, ClassNotFoundException {
        CourseDTO courseDTO=courseBO.searchCourse(id);
        if (courseDTO==null){
            new Alert(Alert.AlertType.WARNING,"Empty Results Set.").show();
        }else{
            txtCourseName.setText(courseDTO.getName());
            txtDuration.setText(String.valueOf(courseDTO.getDuration()));
            txtCourseFee.setText(String.valueOf(courseDTO.getFee()));
            btnSave.setDisable(false);
        }
    }

    private void setStudentData(String id) throws SQLException, ClassNotFoundException {
        StudentDTO studentDTO=studentBO.searchStudent(id);
        if (studentDTO==null){
            new Alert(Alert.AlertType.WARNING,"Empty Results Set.").show();
        }else{
            txtStName.setText(studentDTO.getStName());
        }
    }

    private void loadCourseIds() throws SQLException, ClassNotFoundException {
        List<String> courseIds=courseBO.getCourseIds();
        cmbCourseID.getItems().addAll(courseIds);
    }

    private void loadStudentIds() throws SQLException, ClassNotFoundException {
        List<String> studentIds = studentBO.getStudentIds();
        cmbStudentID.getItems().addAll(studentIds);
    }
    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    public void registerCourseOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        RegisterDetailsDTO registerDetailsDTO=new RegisterDetailsDTO(txtRegID.getText(),cmbCourseID.getValue().toString(),cmbStudentID.getValue().toString(),lblDate.getText());
        registerDetailsBO.saveRegistrationDetails(registerDetailsDTO);
        setRegID();
        new Alert(Alert.AlertType.CONFIRMATION,"Saved.").show();

    }
}
