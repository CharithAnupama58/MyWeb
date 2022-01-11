package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Fertilizer;
import model.Vehicle;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class RemoveFertilizerFormController {
    public JFXTextField txtFertilizerId;
    public JFXTextField txtFertilizerName;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXButton btnRemove;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern fIdPattern = Pattern.compile("^(F-00)[0-9]{1,3}$");
    Pattern quantityPattern = Pattern.compile("^[1-9]{1,2}$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    public void initialize(){
        validateInit();
    }
    private void validateInit() {
        btnRemove.setDisable(true);
        allValidations.put(txtFertilizerId, fIdPattern);
        allValidations.put(txtPackSize, quantityPattern);
        allValidations.put(txtUnitPrice, pricePattern);
    }

    public void searchFertilizer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String fertId = txtFertilizerId.getText();

        Fertilizer f1= new FertilizerController().getFertilizer(fertId);
        if (f1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(f1);
        }
    }

    private void setData(Fertilizer f1) {
        txtFertilizerId.setText(f1.getFertilizerID());
        txtFertilizerName.setText(f1.getFertilizerName());
        txtPackSize.setText(String.valueOf(f1.getPackSize()));
        txtUnitPrice.setText(String.valueOf(f1.getUnitPrice()));
    }

    public void removeFertilizerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new FertilizerController().deleteFertilizer(txtFertilizerId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean){
            btnRemove.setDisable(false);
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
        btnRemove.setDisable(true);
    }
}
