package dao.custom;

import dao.CrudDAO;
import dto.CustomerIncomeDTO;
import dto.OrderDTO;
import entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order,String> {
    boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException;
    String generateNewOrderId() throws SQLException, ClassNotFoundException;
    double getCustomerIncome(String id) throws SQLException, ClassNotFoundException;
    List<String> getOrderIds() throws SQLException, ClassNotFoundException;
}
