package model;

public class BestCustomer {
    private String customerID;
    private double totKg;

    public BestCustomer() {
    }

    public BestCustomer(String customerID, double totKg) {
        this.setCustomerID(customerID);
        this.setTotKg(totKg);
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public double getTotKg() {
        return totKg;
    }

    public void setTotKg(double totKg) {
        this.totKg = totKg;
    }
}
