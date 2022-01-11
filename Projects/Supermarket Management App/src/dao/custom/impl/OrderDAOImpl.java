package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import dto.CustomerDTO;
import dto.CustomerIncomeDTO;
import dto.OrderDTO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT OrderId FROM `Order` WHERE OrderId=?", oid).next();
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1;");
        return rst.next() ? String.format("OD%03d", (Integer.parseInt(rst.getString("orderId").replace("OD", "")) + 1)) : "OD001";
    }

    @Override
    public double getCustomerIncome(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(COST) FROM `Order` WHERE CustID=?",id);
        while (rst.next()){
            return rst.getDouble(1);
        }
        return 0;
    }

    @Override
    public List<String> getOrderIds() throws SQLException, ClassNotFoundException {
        List<String> ids = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order`");
        while (rst.next()) {
            ids.add(rst.getString("OrderId"));
        }
        return ids;
    }

    @Override
    public boolean add(Order orderDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` (OrderId,CustID,orderDate,`time`,cost) VALUES (?,?,?,?,?)",orderDTO.getOrderId(),orderDTO.getCustomerId(),orderDTO.getOrderDate(),orderDTO.getOrderTime(),orderDTO.getCost());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Order` WHERE OrderId=?", id);
    }

    @Override
    public boolean update(Order orderDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` WHERE orderId=?", id);
        rst.next();
        return new Order(id, rst.getString("CustID"), rst.getString("orderDate"), rst.getString("time"),rst.getDouble("cost"));
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order`");
        while (rst.next()) {
            allOrders.add(new Order(rst.getString("OrderId"), rst.getString("CustID"), rst.getString("orderDate"), rst.getString("time"), rst.getDouble("cost")));
        }
        return allOrders;
    }
}
