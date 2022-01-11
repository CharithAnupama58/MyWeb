package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.MonthlyIncomeTM;

import java.sql.SQLException;

public class MonthlyKiloFormController {
    public TableView<MonthlyIncomeTM> tblMonthly;
    public TableColumn colMonth;
    public TableColumn colKg;
    public Label txtMonth;

    public void initialize() throws SQLException, ClassNotFoundException {
        colMonth.setCellValueFactory(new PropertyValueFactory<>("date"));
        colKg.setCellValueFactory(new PropertyValueFactory<>("kilo"));
        ObservableList<MonthlyIncomeTM> income=new CustomerController().getIncome();
        tblMonthly.setItems(income);
    }
}
