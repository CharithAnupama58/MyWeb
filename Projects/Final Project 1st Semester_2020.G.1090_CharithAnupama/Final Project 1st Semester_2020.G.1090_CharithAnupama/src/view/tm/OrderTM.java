package view.tm;

import java.awt.*;

public class OrderTM {
    private String orderId;
    private String customerId;
    private String fertilizerId;
    private double unitPriceForAPack;
    private int quantity;
    private double total;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public OrderTM() {
    }

    public OrderTM(String orderId, String customerId, String fertilizerId, double unitPriceForAPack, int quantity, double total,String date) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setFertilizerId(fertilizerId);
        this.setUnitPriceForAPack(unitPriceForAPack);
        this.setQuantity(quantity);
        this.setTotal(total);
        this.setDate(date);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFertilizerId() {
        return fertilizerId;
    }

    public void setFertilizerId(String fertilizerId) {
        this.fertilizerId = fertilizerId;
    }

    public double getUnitPriceForAPack() {
        return unitPriceForAPack;
    }

    public void setUnitPriceForAPack(double unitPriceForAPack) {
        this.unitPriceForAPack = unitPriceForAPack;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
