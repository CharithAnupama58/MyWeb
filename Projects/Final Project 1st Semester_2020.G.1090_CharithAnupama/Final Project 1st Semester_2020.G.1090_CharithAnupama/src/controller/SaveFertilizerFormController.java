package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Fertilizer;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SaveFertilizerFormController {
    public JFXTextField txtFertilizerId;
    public JFXTextField txtFertilizerName;
    public JFXTextField txtFertilizerPackSize;
    public JFXTextField txtFertilizerUnitPrice;
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern fIdPattern = Pattern.compile("^(F-00)[0-9]{1,3}$");
    Pattern quantityPattern = Pattern.compile("^[1-9]{1,2}$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    public void initialize(){
        validateInit();
    }
    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtFertilizerId, fIdPattern);
        allValidations.put(txtFertilizerPackSize, quantityPattern);
        allValidations.put(txtFertilizerUnitPrice, pricePattern);
    }


    public void saveFertilizerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Fertilizer f1=new Fertilizer(txtFertilizerId.getText(),txtFertilizerName.getText(),Integer.parseInt(txtFertilizerPackSize.getText()),Double.parseDouble(txtFertilizerUnitPrice.getText()));

        String fetId=txtFertilizerId.getText();

        Fertilizer f2=new FertilizerController().getFertilizer(fetId);
        if (f2==null){
            new FertilizerController().saveFertilizer(f1);
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Fertilizer ID Already Exists.");
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
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
