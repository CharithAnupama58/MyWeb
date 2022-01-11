package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Driver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AddDriverPageFormController {
    public AnchorPane addDriverPageContext;
    public JFXTextField txtName;
    public JFXTextField txtNic;
    public JFXTextField txtLicenseNo;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXButton btnSaveDriver;

    static ArrayList<Driver> driverList=new ArrayList();



    public void saveDriverOnAction(ActionEvent actionEvent) {
        if (btnSaveDriver.getText().equalsIgnoreCase("Add Driver")) {

            Driver driver = new Driver(txtName.getText(), txtNic.getText(), txtLicenseNo.getText(), txtAddress.getText(),txtContact.getText());

            if (isExists(driver)) {
                if (driverList.add(driver)) {
                    //alert->save
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.CLOSE).show();
                    clearText();
                } else {
                    //alert->try again
                    new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.CLOSE).show();
                }
            } else {
                    new Alert(Alert.AlertType.WARNING, "Already Exists", ButtonType.CLOSE).show();
            }
        }else {

        }
    }

    private boolean isExists(Driver driver) {
        for (Driver t:driverList
        ) {
            if (t.getNic().equalsIgnoreCase(driver.getNic())) {
                return false;
            }
        }
        return true;
    }


    public void backToManagementPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManagementPageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)addDriverPageContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
    public void clearText(){
        txtName.clear();
        txtNic.clear();
        txtLicenseNo.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    public void moveToLisenceNo(ActionEvent actionEvent) {
        txtLicenseNo.requestFocus();
    }

    public void moveToAddress(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void moveToContact(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void moveToNic(ActionEvent actionEvent) {
        txtNic.requestFocus();
    }
}
