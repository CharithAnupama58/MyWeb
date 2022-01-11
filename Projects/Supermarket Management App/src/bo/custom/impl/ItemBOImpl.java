package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item i : all) {
            allItems.add(new ItemDTO(i.getItemCode(), i.getDescription(),i.getPackSize(),i.getUnitPrice(), i.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(dto.getItemCode(), dto.getDescription(),dto.getPackSize(), dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getItemCode(), dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean ifItemExist(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(id);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public int getQtyOnHand(String itemId) throws SQLException, ClassNotFoundException {
        return itemDAO.getQtyOnHand(itemId);
    }

    @Override
    public ItemDTO getMostMovableItem() throws SQLException, ClassNotFoundException {
       Item item=itemDAO.getMostMovableItem();
        return new ItemDTO(item.getItemCode(), item.getDescription(),item.getPackSize(),item.getUnitPrice(), item.getQtyOnHand());
    }

    @Override
    public ItemDTO getLeastMovableItem() throws SQLException, ClassNotFoundException {
        Item item=itemDAO.getLeastMovableItem();
        return new ItemDTO(item.getItemCode(), item.getDescription(),item.getPackSize(),item.getUnitPrice(), item.getQtyOnHand());
    }
}
