package dao;

import entity.SuperEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SuperDAO<Entity extends SuperEntity, ID> {
    boolean add(Entity t) throws SQLException, ClassNotFoundException;

    boolean delete(ID id) throws SQLException, ClassNotFoundException;

    boolean update(Entity t) throws SQLException, ClassNotFoundException;

    Entity search(ID id) throws SQLException, ClassNotFoundException;

    ArrayList<Entity> getAll() throws SQLException, ClassNotFoundException;
}
