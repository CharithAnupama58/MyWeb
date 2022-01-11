package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer dto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.executeUpdate("INSERT INTO Customer (CustID,CustTitle,CustName,CustAddress,City,Province,PostalCode) VALUES (?,?,?,?,?,?,?)", dto.getCustomerId(), dto.getTitle(), dto.getCustomerName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode());
          Customer customer=new Customer(dto.getCustomerId(), dto.getTitle(), dto.getCustomerName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode());
          Session session = FactoryConfiguration.getInstance().getSession();
          Transaction transaction = session.beginTransaction();

          session.save(customer);
          transaction.commit();

          session.close();
          return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE CustID=?", id);
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET CustTitle=?,CustName=?, CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID=?", customer.getTitle(), customer.getCustomerName(), customer.getAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode(), customer.getCustomerId());
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE CustID=?", id);
        if (rst.next()) {
            return new Customer(id, rst.getString("CustTitle"), rst.getString("CustName"), rst.getString("CustAddress"), rst.getString("City"), rst.getString("Province"), rst.getString("PostalCode"));
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString("CustID"), rst.getString("CustTitle"), rst.getString("CustName"), rst.getString("CustAddress"), rst.getString("City"), rst.getString("Province"), rst.getString("PostalCode")));
        }
        return allCustomers;
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT CustID FROM Customer WHERE CustID=?", id).next();
    }


    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> ids = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            ids.add(rst.getString("CustID"));
        }
        return ids;
    }
}
