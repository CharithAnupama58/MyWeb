package dao.custom;

import dao.CrudDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;
    List<String> getCustomerIds() throws SQLException, ClassNotFoundException;
}
