package model;

public class Fuel {
    private String fuelId;
    private String date;
    private double cost;
    private String vehicleId;

    public Fuel() {
    }

    public Fuel(String fuelId, String date, double cost, String vehicleId) {
        this.setFuelId(fuelId);
        this.setDate(date);
        this.setCost(cost);
        this.setVehicleId(vehicleId);
    }

    public String getFuelId() {
        return fuelId;
    }

    public void setFuelId(String fuelId) {
        this.fuelId = fuelId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
