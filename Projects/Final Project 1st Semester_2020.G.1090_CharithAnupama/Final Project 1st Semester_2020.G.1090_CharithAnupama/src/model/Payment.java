package model;

public class Payment {
    private String payID;
    private String customerID;
    private double totalQty;
    private double unitPrice;
    private double totPayment;
    private String pDate;

    public Payment() {
    }

    public Payment(String payID, String customerID, double totalQty, double unitPrice, double totPayment, String pDate) {
        this.setPayID(payID);
        this.setCustomerID(customerID);
        this.setTotalQty(totalQty);
        this.setUnitPrice(unitPrice);
        this.setTotPayment(totPayment);
        this.setpDate(pDate);
    }

    public String getPayID() {
        return payID;
    }

    public void setPayID(String payID) {
        this.payID = payID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(double totalQty) {
        this.totalQty = totalQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotPayment() {
        return totPayment;
    }

    public void setTotPayment(double totPayment) {
        this.totPayment = totPayment;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }
}
