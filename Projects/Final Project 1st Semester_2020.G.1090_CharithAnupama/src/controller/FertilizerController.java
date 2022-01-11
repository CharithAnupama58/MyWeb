package controller;

import db.DbConnection;
import model.Fertilizer;
import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FertilizerController {
    public Fertilizer getFertilizer(String fetId) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Fertilizer WHERE FertID=?");
        stm.setObject(1, fetId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Fertilizer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );

        } else {
            return null;
        }
    }

    public boolean saveFertilizer(Fertilizer f1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Fertilizer VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,f1.getFertilizerID());
        stm.setObject(2,f1.getFertilizerName());
        stm.setObject(3,f1.getPackSize());
        stm.setObject(4,f1.getUnitPrice());
        return stm.executeUpdate()>0;
    }

    public boolean deleteFertilizer(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Fertilizer WHERE FertID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public List<String> getFertilizerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Fertilizer").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
}
