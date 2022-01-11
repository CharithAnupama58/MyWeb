package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item (Itemcode,Description,PackSize,UnitPrice,QtyOnHand) VALUES (?,?,?,?,?)", dto.getItemCode(), dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(), dto.getQtyOnHand());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE Itemcode=?", id);
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET Description=?,PackSize=?, UnitPrice=?,QtyOnHand=? WHERE Itemcode=?", item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand(),item.getItemCode());
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE Itemcode=?", id);
        if(rst.next()){
            return new Item(id, rst.getString("Description"), rst.getString("PackSize"), rst.getDouble("UnitPrice"), rst.getInt("QtyOnHand"));
        }else{
            return null;
        }

    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            items.add(new Item(rst.getString("Itemcode"), rst.getString("Description"), rst.getString("PackSize"), rst.getDouble("UnitPrice"), rst.getInt("QtyOnHand")));
        }
        return items;
    }

    @Override
    public boolean ifItemExist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT Itemcode FROM Item WHERE Itemcode=?", id).next();
    }

    @Override
    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        List<String> ids = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            ids.add(rst.getString("Itemcode"));
        }
        return ids;
    }

    @Override
    public int getQtyOnHand(String itemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE Itemcode=?", itemId);
        int qtyOnHand=0;
        rst.next();
        qtyOnHand=rst.getInt("QtyOnHand");
        return qtyOnHand;
    }

    @Override
    public Item getMostMovableItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item ORDER BY QtyOnHand ASC LIMIT 1");
        rst.next();
        return new Item(rst.getString("Itemcode"), rst.getString("Description"), rst.getString("PackSize"), rst.getDouble("UnitPrice"), rst.getInt("QtyOnHand"));
    }

    @Override
    public Item getLeastMovableItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item ORDER BY QtyOnHand DESC LIMIT 1");
        rst.next();
        return new Item(rst.getString("Itemcode"), rst.getString("Description"), rst.getString("PackSize"), rst.getDouble("UnitPrice"), rst.getInt("QtyOnHand"));
    }
}
