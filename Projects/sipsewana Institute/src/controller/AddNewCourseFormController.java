package controller;

import business.BOFactory;
import business.custom.CourseBO;
import business.custom.StudentBO;
import business.custom.impl.CourseBOImpl;
import business.custom.impl.StudentBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CourseDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddNewCourseFormController {
    public JFXTextField txtCId;
    public JFXTextField txtCName;
    public JFXTextField txtDuration;
    public JFXTextField txtFee;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(C-00)[0-9]{1,3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern durationPattern = Pattern.compile("^^[1-9]{1,2}$");
    Pattern feePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    private final CourseBO courserBO=(CourseBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.COURSE);

    public void initialize(){
        validateInit();
    }

    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtCId, idPattern);
        allValidations.put(txtCName, namePattern);
        allValidations.put(txtDuration, durationPattern);
        allValidations.put(txtFee ,feePattern);
    }

    public void saveCourseOnAction(ActionEvent actionEvent) {
        CourseDTO courseDTO=new CourseDTO(txtCId.getText(),txtCName.getText(),Integer.parseInt(txtDuration.getText()),Double.parseDouble(txtFee.getText()));
        try {
            courserBO.addCourse(courseDTO);
            new Alert(Alert.AlertType.CONFIRMATION,"Saved.").show();
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.WARNING,"Already Exists.").show();
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING,"Already Exists.").show();
        }
    }
    public void textFields_Key_Realeased(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean){
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
        btnSave.setDisable(true);
    }
}
