package controller;

import business.BOFactory;
import business.custom.StudentBO;
import business.custom.impl.StudentBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.StudentDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class StudentPageFormController {
    public JFXTextField txtStID;
    public JFXTextField txtStName;
    public JFXTextField txtStAddress;
    public JFXTextField txtStNic;
    public JFXTextField txtStTell;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(S-00)[0-9]{1,3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern nicPattern = Pattern.compile("^^[A-z0-9]{4,30}$");
    Pattern tellPattern = Pattern.compile("^^[0-9]{6,30}$");
    private final StudentBO studentBO=(StudentBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.STUDENT);

    public void initialize(){
        validateInit();
    }

    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtStID, idPattern);
        allValidations.put(txtStName, namePattern);
        allValidations.put(txtStAddress, addressPattern);
        allValidations.put(txtStNic ,nicPattern);
        allValidations.put(txtStTell ,tellPattern);
    }

    public void saveStudentOnAction(ActionEvent actionEvent) {
        StudentDTO s1=new StudentDTO(txtStID.getText(),txtStName.getText(),txtStAddress.getText(),txtStNic.getText(),txtStTell.getText());
        try {
            studentBO.addStudent(s1);
            new Alert(Alert.AlertType.CONFIRMATION,"Saved.").show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            new Alert(Alert.AlertType.WARNING,"Already Exists.").show();
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
