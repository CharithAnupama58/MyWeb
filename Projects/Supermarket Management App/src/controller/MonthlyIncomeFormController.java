package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import bo.custom.PurchaseOrderBO;
import bo.custom.impl.PurchaseOrderBOImpl;
import dto.OrderDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.MonthlyIncome;
import view.tm.MonthlyIncomeTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class MonthlyIncomeFormController {
    public TableView<MonthlyIncomeTM> tblIncome;
    public TableColumn colMonth;
    public TableColumn colIncome;
    final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PURCHASE_ORDER);

    public void initialize() {
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("income"));
    }

    ObservableList<MonthlyIncomeTM> monthlyIncomeList = FXCollections.observableArrayList();

    public void loadDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<MonthlyIncome> income = new ArrayList<>();
        ArrayList<OrderDTO> orderDTO=purchaseOrderBO.getAllOrders();
        for (int i = 0; i < 13; i++) {
            income.add(new MonthlyIncome(i,0.0));
        }


        for (OrderDTO o: orderDTO) {
            String[] d=o.getOrderDate().split("-");
            System.out.println(d[1]);
            for (int i = 0; i < income.size(); i++) {
                if (Integer.parseInt(d[1])==i+1){
                    income.get(i).setIncome(income.get(i).getIncome()+o.getCost());
                }
            }
        }
        income.forEach(e -> {
            MonthlyIncomeTM tm = new MonthlyIncomeTM(e.getMonth(), e.getIncome());
            monthlyIncomeList.add(tm);
        });
        tblIncome.setItems(monthlyIncomeList);
    }
}
