package dao.custom;

import dao.CrudDAO;
import dto.ItemDetailsDTO;
import entity.ItemDetails;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDetailsDAO extends CrudDAO<ItemDetails,String> {
    ArrayList<ItemDetails> getOrderItems(String id) throws SQLException, ClassNotFoundException;
    int getOrderQty(String id) throws SQLException, ClassNotFoundException;
}
