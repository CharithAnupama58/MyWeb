package model;

public class Van extends Vehicle {
    String vehicleNum;
    String vehicleType;
    String maxWeight;
    String noOfPassengers;

    public Van(String vehicleNum, String vehicleType, String maxWeight, String noOfPassengers, String vehicleNum1, String vehicleType1, String maxWeight1, String noOfPassengers1) {
        super(vehicleNum, vehicleType, maxWeight, noOfPassengers);
        this.vehicleNum = vehicleNum1;
        this.vehicleType = vehicleType1;
        this.maxWeight = maxWeight1;
        this.noOfPassengers = noOfPassengers1;
    }

    public Van(String vehicleNum, String vehicleType, String maxWeight, String noOfPassengers) {
        super(vehicleNum, vehicleType, maxWeight, noOfPassengers);
    }
}
