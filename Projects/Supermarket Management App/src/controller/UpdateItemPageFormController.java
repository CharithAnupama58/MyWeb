package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import bo.custom.PurchaseOrderBO;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PurchaseOrderBOImpl;
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

public class UpdateItemPageFormController {
    public AnchorPane updatePageContext;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PURCHASE_ORDER);
    private final ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);
    public JFXButton btnUpdate;
    LinkedHashMap<JFXTextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(I-00)[0-9]{1,3}$");



    public void initialize(){
        validateInit();

    }

    private void validateInit() {
        btnUpdate.setDisable(true);
        allValidations.put(txtItemCode, idPattern);
    }

    public void searchItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemId = txtItemCode.getText();

        ItemDTO i1=purchaseOrderBO.searchItem(itemId);
        if (i1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(i1);
        }
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO i1= new ItemDTO(
                txtItemCode.getText(),txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnHand.getText())
        );


        if (itemBO.updateItem(i1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
    }
    void setData(ItemDTO itemDTO){
        txtItemCode.setText(itemDTO.getItemCode());
        txtDescription.setText(itemDTO.getDescription());
        txtPackSize.setText(itemDTO.getPackSize());
        txtUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
    }
    public void textFields_Key_Realeased(KeyEvent keyEvent) {
        Object response = validate();

        if (response instanceof Boolean){
            btnUpdate.setDisable(false);
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField textField = (TextField) response;

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
        btnUpdate.setDisable(true);
    }
}
