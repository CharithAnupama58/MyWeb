package dao.custom;

import dao.CrudDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item,String> {
    boolean ifItemExist(String id) throws SQLException, ClassNotFoundException;
    List<String> getItemIds() throws SQLException, ClassNotFoundException;
    int getQtyOnHand(String itemId) throws SQLException, ClassNotFoundException;
    Item getMostMovableItem() throws SQLException, ClassNotFoundException;
    Item getLeastMovableItem() throws SQLException, ClassNotFoundException;
}
