package controller;

import business.BOFactory;
import business.custom.CourseBO;
import business.custom.RegisterDetailsBO;
import business.custom.StudentBO;
import business.custom.impl.CourseBOImpl;
import business.custom.impl.RegisterDetailsBOImpl;
import business.custom.impl.StudentBOImpl;
import com.jfoenix.controls.JFXComboBox;
import dto.CourseDTO;
import dto.StudentDTO;
import entity.Course;
import entity.Student;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDetailsController {
    public JFXComboBox cmbCourseID;
    public Label txtStudentName;
    public JFXComboBox cmbStudentID;

    private final StudentBO studentBO=(StudentBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.STUDENT);
    private final CourseBO courseBO=(CourseBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.COURSE);
    private final RegisterDetailsBO registerDetailsBO=(RegisterDetailsBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.REGISTERDETAILS);
    public Label txtCourseName;

    public void initialize(){
        try {
            loadStudentIds();
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

    private void loadCourseIds() throws SQLException, ClassNotFoundException {
        List<CourseDTO> s1=registerDetailsBO.getCourseIds(cmbStudentID.getValue().toString());
        List<String> s2=new ArrayList<>();
        for (CourseDTO temp:s1
             ) {
                s2.add(temp.getcID());
        }

        cmbCourseID.getItems().addAll(s2);
       ;
    }

    private void setCourseData(String id) throws SQLException, ClassNotFoundException {
        CourseDTO courseDTO = courseBO.searchCourse(id);
        txtCourseName.setText(courseDTO.getName());

    }

    private void setStudentData(String id) throws SQLException, ClassNotFoundException {
        StudentDTO studentDTO=studentBO.searchStudent(id);
        loadCourseIds();
        if (studentDTO==null){
            new Alert(Alert.AlertType.WARNING,"Empty Results Set.").show();
        }else{
            txtStudentName.setText(studentDTO.getStName());
        }
    }

    private void loadStudentIds() throws SQLException, ClassNotFoundException {
        List<String> studentIds=studentBO.getStudentIds();
        cmbStudentID.getItems().addAll(studentIds);
    }
}
