package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import view.tm.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    public Customer getCustomer(String customerId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Customer WHERE CustID=?");
        stm.setObject(1, customerId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }

    }

    public boolean saveCustomer(Customer c1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Customer VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,c1.getCustomerId());
        stm.setObject(2,c1.getCustomerName());
        stm.setObject(3,c1.getAddress());
        stm.setObject(4,c1.getContact());
        stm.setObject(5,c1.getNic());
        return stm.executeUpdate()>0;
    }

    public boolean updateCustomer(Customer c1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET CustName=?, address=?, contact=?, nic=? WHERE CustID=?");
        stm.setObject(1,c1.getCustomerName());
        stm.setObject(2,c1.getAddress());
        stm.setObject(3,c1.getContact());
        stm.setObject(4,c1.getNic());
        stm.setObject(5,c1.getCustomerId());
        return stm.executeUpdate()>0;
    }

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Customer WHERE CustID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public ObservableList<CustomerTM> getAllCustomers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer");
        ResultSet rst = stm.executeQuery();
        ObservableList<CustomerTM> customers = FXCollections.observableArrayList();
        while (rst.next()) {
            customers.add(new CustomerTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));


        }
        return customers;
    }

    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;

    }

    public String getAdvanceID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT AdvanceID FROM CustomerAdvance ORDER BY AdvanceID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "A-00" + tempId;
            } else if (tempId < 99) {
                return "A-0" + tempId;
            } else {
                return "A-" + tempId;
            }

        } else {
            return "A-001";
        }
    }

    public Advance getAdvance(String customerId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM CustomerAdvance WHERE CustomerID=?");
        stm.setObject(1, customerId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Advance(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3),
                    rst.getString(4)
            );

        } else {
            return null;
        }
    }

    public boolean saveAdvance(Advance a1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO CustomerAdvance VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,a1.getAdvanceID());
        stm.setObject(2,a1.getAdvancePrice());
        stm.setObject(3,a1.getDate());
        stm.setObject(4,a1.getCustomerID());
        return stm.executeUpdate()>0;
    }

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT orderID FROM CustomerFertilizer ORDER BY orderID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "O-00" + tempId;
            } else if (tempId < 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }

        } else {
            return "O-001";
        }
    }

    public boolean saveOrder(Order o1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO CustomerFertilizer VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,o1.getOrderId());
        stm.setObject(2,o1.getCustomerId());
        stm.setObject(3,o1.getFertilizerId());
        stm.setObject(4,o1.getUnitPriceForAPack());
        stm.setObject(5,o1.getQuantity());
        stm.setObject(6,o1.getTotal());
        stm.setObject(7,o1.getDate());
        return stm.executeUpdate()>0;
    }

    public ObservableList<OrderTM> getAllOrders() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM CustomerFertilizer");
        ResultSet rst = stm.executeQuery();
        ObservableList<OrderTM> orders = FXCollections.observableArrayList();
        while (rst.next()) {
            orders.add(new OrderTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6),
                    rst.getString(7)
            ));


        }
        return orders;
    }

    public List<String> getOrderIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM CustomerFertilizer").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public Order getOrder(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM CustomerFertilizer WHERE orderID=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }
    }

    public boolean updateOrder(Order o1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE CustomerFertilizer SET quantity=? total=? WHERE OrderID=?");


        stm.setObject(1,o1.getQuantity());
        stm.setObject(2,o1.getTotal());
        stm.setObject(3,o1.getOrderId());;

        return stm.executeUpdate()>0;
    }

    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM CustomerFertilizer WHERE orderID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public String getObtainID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT obId FROM obtainingLeaves ORDER BY obId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "OB-00" + tempId;
            } else if (tempId <= 99) {
                return "OB-0" + tempId;
            } else {
                return "OB-" + tempId;
            }

        } else {
            return "OB-001";
        }
    }

    public boolean saveObtainingLeaves(ObtainingLeaves b1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO obtainingleaves VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,b1.getObID());
        stm.setObject(2,b1.getCustomerID());
        stm.setObject(3,b1.getEmployeeID());
        stm.setObject(4,b1.getObDate());
        stm.setObject(5,b1.getQuantity());
        return stm.executeUpdate()>0;
    }

    public ObservableList<ObtainingLeavesTM> getAllLeaves(String id,String obtainDate) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM obtainingLeaves WHERE customerID='"+id+"' AND obDate LIKE '"+obtainDate+"%' ");
        ResultSet rst = stm.executeQuery();
        ObservableList<ObtainingLeavesTM> leaves = FXCollections.observableArrayList();
        while (rst.next()) {
            leaves.add(new ObtainingLeavesTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            ));


        }
        return leaves;
    }

    public String getPaymentID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT payID FROM customerpayment ORDER BY payID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "P-00" + tempId;
            } else if (tempId < 99) {
                return "P-0" + tempId;
            } else {
                return "P-" + tempId;
            }

        } else {
            return "P-001";
        }
    }

    public Payment getMonth(String date, String customerId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM customerpayment WHERE customerID=? AND pDate=?");
        stm.setObject(1, customerId);
        stm.setObject(2, date);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }
    }

    public boolean saveCustomerPayment(Payment p1) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO customerpayment VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, p1.getPayID());
        stm.setObject(2, p1.getCustomerID());
        stm.setObject(3, p1.getTotalQty());
        stm.setObject(4, p1.getUnitPrice());
        stm.setObject(5, p1.getTotPayment());
        stm.setObject(6, p1.getpDate());

        return stm.executeUpdate() > 0;
    }

    public List<String> getPaymentIDS() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM customerpayment").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public Payment getPaymentData(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM customerpayment WHERE payID=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }

    }

    public Advance getAdvanceDetails(String id, String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customeradvance WHERE customerID='"+id+"' AND AdvanceDate LIKE '"+date+"%' ");
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Advance(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3),
                    rst.getString(4)
            );

        }else{
            return null;

        }
    }

    public ObservableList<Order>  getOrderData(String id, String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customerfertilizer WHERE custID='"+id+"' AND fDate LIKE '"+date+"%' ");
        ResultSet rst = stm.executeQuery();
        ObservableList<Order> orders = FXCollections.observableArrayList();
        while (rst.next()) {
            orders.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6),
                    rst.getString(7)
            ));


        }
        return orders;

        }

    public boolean updateCustomerPayment(Payment p1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE customerpayment SET totalQuantity=?,totalPayment=? WHERE payID=?");


        stm.setObject(1,p1.getTotalQty());
        stm.setObject(2,p1.getTotPayment());
        stm.setObject(3,p1.getPayID());;

        return stm.executeUpdate()>0;
    }

    public ObservableList<BestCustomer> getBestCustomer(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT customerID,SUM(quantity) FROM ObtainingLeaves  WHERE obDate LIKE '"+date+"%' GROUP BY customerID");
        ResultSet rst = stm.executeQuery();
        ObservableList<BestCustomer> customers = FXCollections.observableArrayList();
        while (rst.next()) {
            customers.add(new BestCustomer(
                    rst.getString(1),
                    rst.getDouble(2)
            ));


        }
        return customers;
    }

    public double getTotalKg(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(quantity) FROM ObtainingLeaves WHERE obDate LIKE '"+date+"%' ");
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getDouble(1);
        } else {
            return 0;
        }
    }

    public double getFuelPrice(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(cost) FROM fuel WHERE fDate LIKE '"+date+"%' ");
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getDouble(1);
        } else {
            return 0;
        }
    }

    public double getCustPayPrice(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(totalPayment) FROM customerpayment WHERE pDate LIKE '"+date+"%' ");
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getDouble(1);
        } else {
            return 0;
        }
    }

    public double getEmpPay(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(total) FROM Salary WHERE `month` LIKE '"+date+"%' ");
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getDouble(1);
        } else {
            return 0;
        }
    }

    public ObservableList<MonthlyIncomeTM> getIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT pDate,SUM(totalQuantity) FROM customerpayment GROUP BY pDate");
        ResultSet rst = stm.executeQuery();
        ObservableList<MonthlyIncomeTM> income = FXCollections.observableArrayList();
        while (rst.next()) {
            income.add(new MonthlyIncomeTM(
                    rst.getString(1),
                    rst.getString(2)
            ));


        }
        return income;

    }

    public ObservableList<AnnualIncomeTM> getAnnualIncome(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(totalQuantity) FROM customerpayment  WHERE pDate LIKE '"+year+"%' ");
        ResultSet rst = stm.executeQuery();
        ObservableList<AnnualIncomeTM> customers = FXCollections.observableArrayList();
        if (rst.next()) {
            String s1=rst.getString(1);
            customers.add(new AnnualIncomeTM(year,s1));
        }
        return customers;
    }
}
