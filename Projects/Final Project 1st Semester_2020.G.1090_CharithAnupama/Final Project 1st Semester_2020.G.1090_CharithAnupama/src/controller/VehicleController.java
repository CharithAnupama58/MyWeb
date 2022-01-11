package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;
import model.Fuel;
import model.Vehicle;
import view.tm.EmployeeTM;
import view.tm.VehicleTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleController {

    public boolean saveVehicle(Vehicle v1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Vehicle VALUES(?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,v1.getVehicleId());
        stm.setObject(2,v1.getVehicleNumber());
        stm.setObject(3,v1.getEmpID());
        return stm.executeUpdate()>0;
    }

    public Vehicle getVehicle(String vehicleId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Vehicle WHERE vehicleID=?");
        stm.setObject(1, vehicleId);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );

        } else {
            return null;
        }
    }
    public Vehicle getVehicleDetails(String employeeID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Vehicle WHERE EmployeeID=?");
        stm.setObject(1, employeeID);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );

        } else {
            return null;
        }
    }

    public boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Vehicle WHERE vehicleID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public ObservableList<VehicleTM> getAllVehicles() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle");
        ResultSet rst = stm.executeQuery();
        ObservableList<VehicleTM> vehicles = FXCollections.observableArrayList();
        while (rst.next()) {
            vehicles.add(new VehicleTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));


        }
        return vehicles;
    }

    public boolean saveFuelRecord(Fuel f1) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Fuel VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,f1.getFuelId());
        stm.setObject(2,f1.getDate());
        stm.setObject(3,f1.getCost());
        stm.setObject(4,f1.getVehicleId());
        return stm.executeUpdate()>0;
    }

    public String getFuelID() throws SQLException, ClassNotFoundException {

        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT FuelID FROM Fuel ORDER BY FuelID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "F-00" + tempId;
            } else if (tempId < 99) {
                return "F-0" + tempId;
            } else {
                return "F-" + tempId;
            }

        } else {
            return "F-001";
        }
    }
}
