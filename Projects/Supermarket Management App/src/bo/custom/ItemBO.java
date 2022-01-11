package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean ifItemExist(String id) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    int getQtyOnHand(String itemId) throws SQLException, ClassNotFoundException;

    ItemDTO getMostMovableItem() throws SQLException, ClassNotFoundException;

    ItemDTO getLeastMovableItem() throws SQLException, ClassNotFoundException;
}
