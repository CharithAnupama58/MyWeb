package view.tm;

public class ObtainingLeavesTM {
    private String obID;
    private String customerID;
    private String employeeID;
    private String obDate;
    private double quantity;

    public ObtainingLeavesTM() {
    }

    public ObtainingLeavesTM(String obID, String customerID, String employeeID, String obDate, double quantity) {
        this.setObID(obID);
        this.setCustomerID(customerID);
        this.setEmployeeID(employeeID);
        this.setObDate(obDate);
        this.setQuantity(quantity);
    }

    public String getObID() {
        return obID;
    }

    public void setObID(String obID) {
        this.obID = obID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getObDate() {
        return obDate;
    }

    public void setObDate(String obDate) {
        this.obDate = obDate;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
