package view.tm;

public class VehicleTM {

    private String vehicleId;
    private String vehicleNumber;
    private String empID;

    public VehicleTM() {
    }

    public VehicleTM(String vehicleId, String vehicleNumber, String empID) {
        this.setVehicleId(vehicleId);
        this.setVehicleNumber(vehicleNumber);
        this.setEmpID(empID);
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }
}
