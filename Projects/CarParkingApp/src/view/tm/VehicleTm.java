package view.tm;

public class VehicleTm {
    String vehicleNum;
    String vehicleType;
    String maxWeight;
    String noOfPassengers;

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(String noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }

    public VehicleTm() {
    }

    public VehicleTm(String vehicleNum, String vehicleType, String maxWeight, String noOfPassengers) {
        this.vehicleNum = vehicleNum;
        this.vehicleType = vehicleType;
        this.maxWeight = maxWeight;
        this.noOfPassengers = noOfPassengers;
    }
}

