package entity;

import dto.ItemDTO;

import java.util.Objects;

public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private double unitPrice;
    private int qtyOnHand;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item itemDTO = (Item) o;
        return qtyOnHand == itemDTO.qtyOnHand &&
                Objects.equals(itemCode, itemDTO.itemCode) &&
                Objects.equals(description, itemDTO.description) &&
                Objects.equals(packSize, itemDTO.packSize) &&
                Objects.equals(unitPrice, itemDTO.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, description, packSize, unitPrice, qtyOnHand);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + getItemCode() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", packSize='" + getPackSize() + '\'' +
                ", unitPrice=" + getUnitPrice() +
                ", qtyOnHand=" + getQtyOnHand() +
                '}';
    }

    public Item(String itemCode, String description, String packSize, double unitPrice, int qtyOnHand) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setPackSize(packSize);
        this.setUnitPrice(unitPrice);
        this.setQtyOnHand(qtyOnHand);
    }

    public Item() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }
}
