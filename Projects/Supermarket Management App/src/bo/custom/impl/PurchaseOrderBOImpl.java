package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailsDAOImpl;
import db.DbConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import entity.Customer;
import entity.Item;
import entity.ItemDetails;
import entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = null;

        connection = DbConnection.getInstance().getConnection();
        boolean orderAvailable = orderDAO.ifOrderExist(dto.getOrderId());
        /*if order id already exist*/
        if (orderAvailable) {
            return false;
        }

        connection.setAutoCommit(false);
        /*Add Order*/
        Order order = new Order(dto.getOrderId(),dto.getCustomerId(),dto.getOrderDate(),dto.getOrderTime(),dto.getCost());
        boolean orderAdded = orderDAO.add(order);
        if (!orderAdded) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (ItemDetailsDTO detail : dto.getItems()) {
            ItemDetails orderDetailDTO = new ItemDetails(detail.getItemCode(),dto.getOrderId(), detail.getUnitPrice(),detail.getQtyForSell(),detail.getDiscount());
            boolean orderDetailsAdded = orderDetailsDAO.add(orderDetailDTO);
            if (!orderDetailsAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            Item search = itemDAO.search(detail.getItemCode());
            search.setQtyOnHand(search.getQtyOnHand() - detail.getQtyForSell());
            boolean update = itemDAO.update(search);
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        //if every thing ok
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewOrderId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCustomerId(), c.getTitle(), c.getCustomerName(),c.getAddress(),c.getCity(),c.getProvince(),c.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getDescription(),item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        if (item==null){
            return null;

        }else{
            return new ItemDTO(item.getItemCode(), item.getDescription(),item.getPackSize(),item.getUnitPrice(), item.getQtyOnHand());
        }
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(code);
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(id);
    }

    @Override
    public CustomerDTO searchCustomer(String s) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(s);
        if (c==null){
            return null;
        }else {
            return new CustomerDTO(c.getCustomerId(), c.getTitle(), c.getCustomerName(), c.getAddress(), c.getCity(), c.getProvince(), c.getPostalCode());
        }
    }

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerIds();
    }

    @Override
    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getItemIds();
    }

    @Override
    public double getCustomerIncome(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.getCustomerIncome(id);
    }

    @Override
    public boolean deleteOrder(String id,ArrayList<ItemDetailsDTO> item) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        boolean orderRemoved=orderDAO.delete(id);
        if (!orderRemoved) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        for (ItemDetailsDTO temp:item
             ) {
            Item search = itemDAO.search(temp.getItemCode());
            search.setQtyOnHand(search.getQtyOnHand()+temp.getQtyForSell());
            boolean update = itemDAO.update(search);
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

        }

    //if every thing ok
        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }

    @Override
    public OrderDTO searchOrder(String id) throws SQLException, ClassNotFoundException {
        Order order=orderDAO.search(id);
        return new OrderDTO(order.getOrderId(),order.getCustomerId(),order.getOrderDate(),order.getOrderTime(),order.getCost(),null);
    }

    @Override
    public List<String> getOrderIds() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderIds();
    }

    @Override
    public ArrayList<ItemDetailsDTO> getOrderItems(String id) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetailsDTO> order = new ArrayList<>();
        ArrayList<ItemDetails> all = orderDetailsDAO.getOrderItems(id);
        for (ItemDetails c : all) {
            order.add(new ItemDetailsDTO(c.getItemCode(),c.getOid(),c.getUnitPrice(), c.getQtyForSell(),c.getDiscount()));
        }
        return order;
    }
    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> order = new ArrayList<>();
        ArrayList<Order> all = orderDAO.getAll();
        for (Order c : all) {
            order.add(new OrderDTO(c.getOrderId(),c.getCustomerId(), c.getOrderDate(), c.getOrderTime(),c.getCost(),null));
        }
        return order;
    }
}
