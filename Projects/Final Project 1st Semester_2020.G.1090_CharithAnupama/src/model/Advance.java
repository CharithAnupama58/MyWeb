package model;

public class Advance {
    private String advanceID;
    private double advancePrice;
    private String date;
    private String customerID;

    public Advance() {
    }

    public Advance(String advanceID, double advancePrice, String date, String customerID) {
        this.setAdvanceID(advanceID);
        this.setAdvancePrice(advancePrice);
        this.setDate(date);
        this.setCustomerID(customerID);
    }

    public String getAdvanceID() {
        return advanceID;
    }

    public void setAdvanceID(String advanceID) {
        this.advanceID = advanceID;
    }

    public double getAdvancePrice() {
        return advancePrice;
    }

    public void setAdvancePrice(double advancePrice) {
        this.advancePrice = advancePrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
