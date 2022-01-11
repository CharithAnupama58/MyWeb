package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailsDAO;
import dto.CustomerDTO;
import dto.ItemDetailsDTO;
import entity.ItemDetails;
import view.tm.CartTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean add(ItemDetails itemDetailsDTO) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("INSERT INTO OrderDetail (ItemCode,OrderId,OrderQTY,price,Discount) VALUES (?,?,?,?,?)",itemDetailsDTO.getOid(),itemDetailsDTO.getItemCode(),itemDetailsDTO.getQtyForSell(),itemDetailsDTO.getUnitPrice(),itemDetailsDTO.getDiscount());

    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(ItemDetails itemDetailsDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ItemDetails search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ItemDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ItemDetails> getOrderItems(String id) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetails> allCustomers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE orderId=?",id);
        while (rst.next()) {
            allCustomers.add(new ItemDetails(rst.getString("Itemcode"), rst.getString("orderId"),rst.getDouble("price"), rst.getInt("OrderQTY"), rst.getDouble("Discount")));
        }
        return allCustomers;

    }

    @Override
    public int getOrderQty(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderQTY FROM OrderDetail WHERE OrderId=?",id);
        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
    }
}