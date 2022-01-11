package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.EmployeeTM;

import java.sql.SQLException;

public class AllEmployeeFormController {
    public TableView<EmployeeTM> tblEmployee;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colEmpAddress;
    public TableColumn colEmpContact;
    public TableColumn colEmpNic;

    public void initialize() throws SQLException, ClassNotFoundException {

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empID"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmpNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        ObservableList<EmployeeTM> obList=new EmployeeController().getAllEmployees();
        tblEmployee.setItems(obList);
    }
}
