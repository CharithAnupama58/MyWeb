package dto;

public class ItemDetailsDTO {
     private String oid;
     private String itemCode;
     private double unitPrice;
     private int qtyForSell;
     private double discount;

    @Override
    public String toString() {
        return "ItemDetails{" +
                "oid'"+oid+'\''+
                "itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyForSell=" + qtyForSell +
                ", discount=" + discount +
                '}';
    }

    public ItemDetailsDTO() {
    }

    public ItemDetailsDTO(String oid,String itemCode, double unitPrice, int qtyForSell, double discount) {
        this.setOid(oid);
        this.setItemCode(itemCode);
        this.setUnitPrice(unitPrice);
        this.setQtyForSell(qtyForSell);
        this.setDiscount(discount);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyForSell() {
        return qtyForSell;
    }

    public void setQtyForSell(int qtyForSell) {
        this.qtyForSell = qtyForSell;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setOid(String oid) { this.oid = oid; }

    public String getOid() { return oid; }
}
