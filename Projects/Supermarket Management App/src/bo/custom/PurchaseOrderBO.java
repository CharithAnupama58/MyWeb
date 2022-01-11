package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PurchaseOrderBO extends SuperBO {
    boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    String generateNewOrderId()throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers()throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems()throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code)throws SQLException, ClassNotFoundException;

    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String s)throws SQLException, ClassNotFoundException;

    List<String> getCustomerIds() throws SQLException, ClassNotFoundException;

    List<String> getItemIds() throws SQLException, ClassNotFoundException;

    double getCustomerIncome(String id) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String id,ArrayList<ItemDetailsDTO> item) throws SQLException, ClassNotFoundException;

    OrderDTO searchOrder(String id)throws SQLException, ClassNotFoundException;

    List<String> getOrderIds() throws SQLException, ClassNotFoundException;

    ArrayList<ItemDetailsDTO> getOrderItems(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getAllOrders()throws SQLException, ClassNotFoundException;

}
