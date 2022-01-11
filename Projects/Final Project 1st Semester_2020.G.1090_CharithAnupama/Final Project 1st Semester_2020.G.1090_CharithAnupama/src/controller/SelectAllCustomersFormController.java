package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.CustomerTM;
import view.tm.EmployeeTM;

import java.sql.SQLException;

public class SelectAllCustomersFormController {
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colNic;
    public TableColumn colAddress;

    public void initialize() throws SQLException, ClassNotFoundException {
        colID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        ObservableList<CustomerTM> obList=new CustomerController().getAllCustomers();
        tblCustomer.setItems(obList);
    }
}
