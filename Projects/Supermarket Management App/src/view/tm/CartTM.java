package view.tm;

public class CartTM {
     private String itemId;
     private String description;
     private int qty;
     private double unitPrice;
     private double total;
     private double discount;

    @Override
    public String toString() {
        return "CartTM{" +
                "itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", discount=" + discount +
                '}';
    }

    public CartTM() {
    }

    public CartTM(String itemId, String description, int qty, double unitPrice, double total, double discount) {
        this.setItemId(itemId);
        this.setDescription(description);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
        this.setTotal(total);
        this.setDiscount(discount);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
