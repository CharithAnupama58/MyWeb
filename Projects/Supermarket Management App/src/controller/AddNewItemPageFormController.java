package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddNewItemPageFormController {
    public AnchorPane addNewItemContext;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    private final ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);
    public JFXButton btnSave;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(I-00)[0-9]{1,3}$");
    Pattern desPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern packPattern = Pattern.compile("^[1-9]{1,2}$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern qtyPattern = Pattern.compile("^[1-9]{1,4}$");


    public void initialize(){
        validateInit();

    }

    private void validateInit() {
        btnSave.setDisable(true);
        allValidations.put(txtItemCode, idPattern);
        allValidations.put(txtDescription, desPattern);
        allValidations.put(txtPackSize, packPattern);
        allValidations.put(txtUnitPrice, pricePattern);
        allValidations.put(txtQtyOnHand ,qtyPattern);
    }

    public void saveItemOnAction(ActionEvent actionEvent)  {
        ItemDTO i1 = new ItemDTO(
                txtItemCode.getText(),txtDescription.getText(),
                txtPackSize.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnHand.getText())
        );

        try {
            if(itemBO.ifItemExist(txtItemCode.getText())){
                new Alert(Alert.AlertType.WARNING, "Already Exists.").show();
            }else{
                itemBO.addItem(i1);
                new Alert(Alert.AlertType.CONFIRMATION, "Saved.").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
